package com.example.imparkapk.data.repository.usuarios

import android.content.Context
import com.example.imparkapk.data.local.dao.usuarios.ClienteDao
import com.example.imparkapk.data.local.entity.usuarios.ClienteEntity
import com.example.imparkapk.data.mapper.usuarios.toDomain
import com.example.imparkapk.data.mapper.usuarios.toEntity
import com.example.imparkapk.data.remote.api.api.usuarios.ClienteApi
import com.example.imparkapk.data.worker.ClienteSyncScheduler
import com.example.imparkapk.di.IoDispatcher
import com.example.imparkapk.domain.model.enuns.TipoDeUsuario
import com.example.imparkapk.domain.model.usuarios.Cliente
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.text.get

@Singleton
class ClienteRepository @Inject constructor(
    val dao: ClienteDao,
    val api: ClienteApi,
    @IoDispatcher private val io: CoroutineDispatcher,
    @ApplicationContext private val context: Context,
    private val gson: Gson
){
    private val jsonMedia = "application/json".toMediaType()

    private fun partJasonData(dados: Any): RequestBody =
        gson.toJson(dados).toRequestBody(jsonMedia)

    fun observeAll(): Flow<List<Cliente>> = dao.observerAll().map { list -> list.map { it.toDomain() } }

    fun observeById(id: Long): Flow<Cliente?> = dao.observeById(id).map { it?.toDomain() }

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
        nome: String,
        email: String,
        senha: String,
        telefone: String,
        dataNascimento: Date,
    ) : Cliente {
        return withContext(io) {
            val tempId = System.currentTimeMillis()
            val localCliente = ClienteEntity(
                nome = nome,
                id = tempId,
                email = email,
                senha = senha,
                telefone = telefone,
                dataNascimento = dataNascimento,
                tipoUsuario = TipoDeUsuario.CLIENTE,
                carros = emptyList(),
                avaliacoes = emptyList(),
                reservas = emptyList(),
                ativo = true,
                updatedAt = System.currentTimeMillis(),
                pendingSync = true,
                localOnly = true,
                operationType = "CREATE"
            )

            dao.upsert(localCliente)

            ClienteSyncScheduler.enqueueNow(context)

            localCliente.toDomain()
        }
    }

    suspend fun update(
        id: Long,
        nome: String,
        email: String,
        senha: String,
        telefone: String,
        dataNascimento: Date,
    ) : Cliente {
        return withContext(io) {
            val local = dao.getById(id) ?: throw IllegalArgumentException("Cliente não encontrado")
            val updated = local.copy(
                nome = nome,
                id = id,
                email = email,
                senha = senha,
                telefone = telefone,
                dataNascimento = dataNascimento,
                tipoUsuario = TipoDeUsuario.CLIENTE,
                carros = emptyList(),
                avaliacoes = emptyList(),
                reservas = emptyList(),
                ativo = true,
                updatedAt = System.currentTimeMillis(),
                pendingSync = true,
                localOnly = true,
                operationType = "UPDATE"
            )

            dao.upsert(updated)
            ClienteSyncScheduler.enqueueNow(context)
            updated.toDomain()
        }
    }

    suspend fun delete(id: Long): Result<Unit> = runCatching {
        val local = dao.getById(id) ?: return@runCatching
        dao.upsert(
            local.copy(
                ativo = false,
                pendingSync = true,
                updatedAt = System.currentTimeMillis(),
                operationType = "DELETE"
            )
        )
        ClienteSyncScheduler.enqueueNow(context)
    }

    suspend fun sincronizarClientes() {
        val pendentes = dao.getByPending()

        pendentes.filter { it.operationType == "DELETE" && it.ativo }.forEach {
            u ->
            try {
                runCatching { api.delete(u.id) }
                dao.deleteById(u.id)
            } catch (e: Exception) {}
        }

        pendentes.filter { it.operationType == "CREATE" && it.ativo }.forEach {
            u ->
            try {
                val dados = mapOf(
                    "nome" to u.nome,
                    "email" to u.email,
                    "senha" to u.senha,
                    "telefone" to u.telefone,
                    "dataNascimento" to u.dataNascimento,
                    "carros" to u.carros,
                    "avaliacoes" to u.avaliacoes,
                    "reservas" to u.reservas
                )
                val resp = api.create(dadosJson = partJasonData(dados))
                dao.deleteById(u.id)
                dao.upsert(resp.toEntity(pending = false))
            } catch (_: Exception) {}
        }

        pendentes.filter { it.operationType == "UPDATE" && it.ativo }.forEach {
            u ->
            try {
                val dados = buildMap<String, Any> {
                    put("nome", u.nome)
                    put("nome", u.nome)
                    put("email", u.email)
                    put("senha", u.senha)
                    put("telefone", u.telefone)
                    put("dataNascimento", u.dataNascimento)
                    put("carros", u.carros)
                    put("avaliacoes", u.avaliacoes)
                    put("reservas", u.reservas)
                }

                val resp = api.update(
                    u.id,
                    partJasonData(dados)
                )

                dao.upsert(
                    resp.toEntity(
                        false,
                    ).copy(
                        updatedAt = System.currentTimeMillis(),

                    )
                )

            } catch (e: Exception) {}
        }
    }
}