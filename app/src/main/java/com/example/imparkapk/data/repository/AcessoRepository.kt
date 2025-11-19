package com.example.imparkapk.data.repository

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.imparkapk.data.local.dao.AcessoDao
import com.example.imparkapk.data.local.entity.AcessoEntity
import com.example.imparkapk.data.mapper.toDomain
import com.example.imparkapk.data.mapper.toEntity
import com.example.imparkapk.data.remote.api.api.AcessoApi
import com.example.imparkapk.data.worker.acesso.AcessoSyncScheduler
import com.example.imparkapk.di.IoDispatcher
import com.example.imparkapk.domain.model.Acesso
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.sql.Time
import javax.inject.Singleton

@Singleton
class AcessoRepository @Inject constructor(
    private val api: AcessoApi,
    private val dao: AcessoDao,
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

        fun observeUsuarios(): Flow<List<Acesso>> =
            dao.observerAll().map { list -> list.map { it.toDomain() } }

        fun observeUsuario(id: Long): Flow<Acesso?> =
            dao.observeById(id).map { it?.toDomain() }

        suspend fun refresh(): Result<Unit> = runCatching {
            val remote = api.list()
            val current = dao.listAll().associateBy { it.id }

            val merged = remote.map { dto ->
                val old = current[dto.id]
                if (old?.ativo == true) old else dto.toEntity(pending = false)
            }

            dao.upsertAll(merged)

            val remoteIds = merged.map { it.id }.toSet()
            val toDelete = current.values.filter { it.id !in remoteIds && !it.pendingSync && !it.localOnly }
            toDelete.forEach { dao.deleteById(it.id) }
        }


        suspend fun create(
            estacionamentoId: Long,
            carroId: Long,
            placa: String,
            horaDeEntrada: Time,
            horaDeSaida: Time,
            horasTotais: Int,
            valorTotal: Double,
        ): Acesso {
            return withContext(io) {

                val tempId = System.currentTimeMillis()
                val localUsuario = AcessoEntity(
                    id = tempId,
                    updatedAt = System.currentTimeMillis(),
                    pendingSync = true,
                    localOnly = true,
                    ativo = false,
                    operationType = "CREATE",
                    estacionamentoId = estacionamentoId,
                    carroId = carroId,
                    placa = placa,
                    horaDeEntrada = horaDeEntrada,
                    horaDeSaida = horaDeSaida,
                    horasTotais = horasTotais,
                    valorTotal = valorTotal,
                )

                dao.upsert(localUsuario)

                AcessoSyncScheduler.enqueueNow(context)

                localUsuario.toDomain()
            }
        }

        suspend fun update(
            id: Long,
            estacionamentoId: Long,
            carroId: Long,
            placa: String,
            horaDeEntrada: Time,
            horaDeSaida: Time,
            horasTotais: Int,
            valorTotal: Double,
        ): Acesso {
            return withContext(io) {
                val local = dao.getById(id) ?: throw IllegalArgumentException("Usuário não encontrado")
                val updated = local.copy(
                    estacionamentoId = estacionamentoId,
                    carroId = carroId,
                    placa = placa,
                    horaDeEntrada = horaDeEntrada,
                    horaDeSaida = horaDeSaida,
                    horasTotais = horasTotais,
                    valorTotal = valorTotal,
                    updatedAt = System.currentTimeMillis(),
                    pendingSync = true,
                    localOnly = local.localOnly,
                    ativo = false,
                    operationType = "UPDATE"
                )

                dao.upsert(updated)
                AcessoSyncScheduler.enqueueNow(context)
                updated.toDomain()
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
            AcessoSyncScheduler.enqueueNow(context)
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
                        "estacionamentoId" to u.estacionamentoId,
                        "carroId" to u.carroId,
                        "placa" to u.placa,
                        "horaDeEntrada" to u.horaDeEntrada,
                        "horaDeSaida" to u.horaDeSaida,
                        "horasTotais" to u.horasTotais,
                        "valorTotal" to u.valorTotal,
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
                        put("horaDeEntrada", u.horaDeEntrada)
                        put("horaDeSaida", u.horaDeSaida)
                        put("horasTotais", u.horasTotais)
                        put("valorTotal", u.valorTotal)
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
                    Log.w("UsuarioRepository", "Falha ao sincronizar UPDATE ${u.id}: ${e.message}")
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
                            "estacionamentoId" to e.estacionamentoId,
                            "carroId" to e.carroId,
                            "placa" to e.placa,
                            "horaDeEntrada" to e.horaDeEntrada,
                            "horaDeSaida" to e.horaDeSaida,
                            "horasTotais" to e.horasTotais,
                            "valorTotal" to e.valorTotal,
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
                put("horaDeEntrada", e.horaDeEntrada)
                put("horaDeSaida", e.horaDeSaida)
                put("horasTotais", e.horasTotais)
                put("valorTotal", e.valorTotal)
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