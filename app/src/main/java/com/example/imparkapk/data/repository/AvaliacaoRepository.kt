package com.example.imparkapk.data.repository

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.imparkapk.data.local.dao.AvaliacaoDao
import com.example.imparkapk.data.local.entity.AvaliacaoEntity
import com.example.imparkapk.data.mapper.toDomain
import com.example.imparkapk.data.mapper.toEntity
import com.example.imparkapk.data.remote.api.AvaliacaoApi
import com.example.imparkapk.data.worker.avaliacoes.AvaliacoesSyncScheduler
import com.example.imparkapk.di.IoDispatcher
import com.example.imparkapk.domain.model.Avaliacao
import com.google.gson.Gson
import com.rafaelcosta.modelo_app_crud_usuario_api.data.repository.ClienteRepository
import com.rafaelcosta.modelo_app_crud_usuario_api.data.repository.EstacionamentoRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.util.Date
import javax.inject.Inject

class AvaliacaoRepository @Inject constructor(
    private val api: AvaliacaoApi,
    private val dao: AvaliacaoDao,
    private val estacionamentoRepository: EstacionamentoRepository,
    private val clienteRepository: ClienteRepository,
    @IoDispatcher private val io: CoroutineDispatcher,
    @ApplicationContext private val context: Context,
    private val gson: Gson
) {
    private val jsonMedia = "application/json".toMediaType()

    private fun partJsonDados(dados: Any): RequestBody =
        gson.toJson(dados).toRequestBody(jsonMedia)

    private fun partFromUri(fieldName: String, uri: Uri?): MultipartBody.Part? {
        if (uri == null) return null
        val cr: ContentResolver = context.contentResolver
        val type = cr.getType(uri) ?: "application/octet-stream"
        val fileName = uri.lastPathSegment?.substringAfterLast('/') ?: "arquivo"
        val input = cr.openInputStream(uri) ?: return null
        val tmp = File.createTempFile("up_", "_tmp", context.cacheDir)
        tmp.outputStream().use { out -> input.copyTo(out) }
        val body = tmp.asRequestBody(type.toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(fieldName, fileName, body)
    }

    private fun partsFromUris(uris: List<Uri>?): List<MultipartBody.Part>? {
        if (uris.isNullOrEmpty()) return null
        return uris.mapNotNull { partFromUri("anexos", it) }
    }

    fun observeUsuarios(): Flow<List<Avaliacao>> =
        dao.observerAll().map { list ->
            list.map {
                it.toDomain(
                    cliente = clienteRepository.observeUsuario(it.clienteId).toList().first(),
                    estacionamento = estacionamentoRepository.observeEstacionamento(it.estacionamentoId)
                        .toList().first(),
                )
            }
        }

            fun observeUsuario(id: Long): Flow<Avaliacao?> =
                dao.observeById(id).map {
                    it?.toDomain(
                        cliente = clienteRepository.observeUsuario(it.clienteId).toList().first(),
                        estacionamento = estacionamentoRepository.observeEstacionamento(it.estacionamentoId)
                            .toList().first(),
                    )
                }

            suspend fun refresh(): Result<Unit> = runCatching {
                val remote = api.list()
                val current = dao.listAll().associateBy { it.id }

                val merged = remote.map { dto ->
                    val old = current[dto.id]
                    if (old?.ativo == true) old else dto.toEntity(pending = false)
                }

                dao.upsertAll(merged)

                val remoteIds = merged.map { it.id }.toSet()
                val toDelete =
                    current.values.filter { it.id !in remoteIds && !it.pendingSync && !it.localOnly }
                toDelete.forEach { dao.deleteById(it.id) }
            }


            suspend fun create(
                clienteId: Long,
                estacionamentoId: Long,
                nota: Int,
                comentario: String,
                dataAvaliacao: Date
            ): Avaliacao {
                return withContext(io) {

                    val tempId = System.currentTimeMillis()
                    val localUsuario = AvaliacaoEntity(
                        id = tempId,
                        updatedAt = System.currentTimeMillis(),
                        pendingSync = true,
                        localOnly = true,
                        ativo = false,
                        operationType = "CREATE",
                        clienteId = clienteId,
                        estacionamentoId = estacionamentoId,
                        nota = nota,
                        comentario = comentario,
                        dataAvaliacao = dataAvaliacao,
                    )

                    dao.upsert(localUsuario)

                    AvaliacoesSyncScheduler.enqueueNow(context)

                    localUsuario.toDomain(
                        cliente = clienteRepository.observeUsuario(clienteId).toList().first(),
                        estacionamento = estacionamentoRepository.observeEstacionamento(estacionamentoId)
                            .toList().first()
                    )
                }
            }

            suspend fun update(
                id: Long,
            ): Avaliacao {
                return withContext(io) {
                    val local =
                        dao.getById(id) ?: throw IllegalArgumentException("Usuário não encontrado")
                    val updated = local.copy(
                        updatedAt = System.currentTimeMillis(),
                        pendingSync = true,
                        localOnly = local.localOnly,
                        ativo = false,
                        operationType = "UPDATE"
                    )

                    dao.upsert(updated)
                    AvaliacoesSyncScheduler.enqueueNow(context)
                    updated.toDomain(
                        cliente = clienteRepository.observeUsuario(local.clienteId).toList()
                            .first(),
                        estacionamento = estacionamentoRepository.observeEstacionamento(local.estacionamentoId)
                            .toList().first()
                    )
                }
            }


            suspend fun delete(id: Long): Result<Unit> = runCatching {
                val local = dao.getById(id) ?: return@runCatching
                dao.upsert(
                    local.copy(
                        ativo = true,
                        pendingSync = true,
                        updatedAt = System.currentTimeMillis(),
                        operationType = "DELETE"
                    )
                )
                AvaliacoesSyncScheduler.enqueueNow(context)
            }

            suspend fun sincronizarUsuarios() {
                val pendentes = dao.getByPending()

                pendentes.filter { it.operationType == "DELETE" }.forEach { u ->
                    try {
                        runCatching { api.delete(u.id) }
                        dao.deleteById(u.id)
                    } catch (e: Exception) {
                    }
                }

                pendentes.filter { it.operationType == "CREATE" && !it.ativo }.forEach { u ->
                    try {
                        val dados = mapOf(
                            ("clienteId" to u.clienteId),
                            ("estacionamentoId" to u.estacionamentoId),
                            ("nota" to u.nota),
                            ("comentario" to u.comentario),
                            ("dataAvaliacao" to u.dataAvaliacao)
                        )
                        val resp = api.create(
                            dadosJson = partJsonDados(dados),
                        )
                        dao.deleteById(u.id)
                        dao.upsert(resp.toEntity(pending = false))
                    } catch (_: Exception) {
                    }
                }

                pendentes.filter { it.operationType == "UPDATE" && !it.ativo }.forEach { u ->
                    try {
                        val dados = buildMap<String, Any> {
                            put("clienteId", clienteRepository.observeUsuario(u.clienteId))
                            put(
                                "estacionamentoId",
                                estacionamentoRepository.observeEstacionamento(u.estacionamentoId)
                            )
                            put("nota", u.nota)
                            put("comentario", u.comentario)
                            put("dataAvaliacao", u.dataAvaliacao)
                        }

                        val resp = api.update(
                            id = u.id,
                            dadosJson = partJsonDados(dados)
                        )

                        dao.upsert(
                            resp.toEntity(
                                pending = false
                            ).copy(
                                updatedAt = System.currentTimeMillis(),
                                pendingSync = false,
                                localOnly = false,
                                operationType = null
                            )
                        )

                    } catch (e: Exception) {
                        Log.w(
                            "UsuarioRepository",
                            "Falha ao sincronizar UPDATE ${u.id}: ${e.message}"
                        )
                    }
                }

                try {
                    val listaApi = api.list()
                    val atuais = dao.listAll().associateBy { it.id }

                    val remotos = listaApi.map { dto ->
                        val antigo = atuais[dto.id]

                        // 1️⃣ se foi deletado localmente, não ressuscita
                        if (antigo?.ativo == true) return@map antigo

                        val remoto = dto.toEntity(pending = false)

                        // 2️⃣ se o local tem pendingSync, ele é mais novo → mantém local
                        if (antigo?.pendingSync == true) return@map antigo

                        // 3️⃣ se o local tem updatedAt mais recente, mantém local
                        if (antigo != null && antigo.updatedAt > remoto.updatedAt) return@map antigo

                        // 4️⃣ caso contrário, aceita o remoto (API)
                        remoto
                    }

                    dao.upsertAll(remotos)

                    val idsRemotos = remotos.map { it.id }.toSet()
                    val locais = dao.listAll()
                    locais.filter { local ->
                        local.id !in idsRemotos && !local.pendingSync && !local.localOnly
                    }.forEach { dao.deleteById(it.id) }

                } catch (e: Exception) {
                    Log.w("UsuarioRepository", "Sem conexão no pull: ${e.message}")
                }
            }

            @Suppress("unused")
            suspend fun syncAll(): Result<Unit> = runCatching {
                val pendentes = dao.getByPending()

                for (e in pendentes) {
                    try {
                        if (e.localOnly) {
                            val dados = mapOf(
                                ("clienteId" to e.clienteId),
                                ("estacionamentoId" to e.estacionamentoId),
                                ("nota" to e.nota),
                                ("comentario" to e.comentario),
                                ("dataAvaliacao" to e.dataAvaliacao)
                            )
                            val resp = api.create(
                                dadosJson = partJsonDados(dados),
                            )
                            dao.deleteById(e.id)
                            dao.upsert(resp.toEntity(pending = false))
                        } else {
                            val dados = buildMap<String, Any> {
                            }
                            val resp = api.update(
                                id = e.id,
                                dadosJson = partJsonDados(dados)
                            )
                            dao.upsert(resp.toEntity(pending = false))
                        }
                    } catch (_: Exception) {
                    }
                }
                refresh()
            }

            @Suppress("unused")
            private suspend fun tryPushOne(id: Long) {
                val e = dao.getById(id) ?: return

                val dados = buildMap<String, Any> {
                }


                val pushed = if (existsRemote(id)) {
                    api.update(
                        id = id,
                        dadosJson = partJsonDados(dados)
                    )
                } else {
                    api.create(
                        dadosJson = partJsonDados(dados)
                    )
                }

                dao.upsert(pushed.toEntity(pending = false))
            }

            private suspend fun existsRemote(id: Long): Boolean = runCatching {
                api.getById(id); true
            }.getOrDefault(false)

            private fun saveLocalCopy(uri: Uri?): String? {
                if (uri == null) return null
                return try {
                    val cr = context.contentResolver
                    val input = cr.openInputStream(uri) ?: return null
                    val fotosDir = File(context.filesDir, "fotos").apply { mkdirs() }
                    val destFile = File(fotosDir, "foto_${System.currentTimeMillis()}.jpg")
                    input.use { src -> destFile.outputStream().use { dst -> src.copyTo(dst) } }
                    destFile.absolutePath
                } catch (e: Exception) {
                    null
                }
            }
        }