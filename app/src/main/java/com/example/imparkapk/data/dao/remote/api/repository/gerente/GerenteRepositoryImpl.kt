package com.example.imparkapk.data.dao.remote.api.repository.gerente

import com.example.imparkapk.data.dao.local.dao.dao.GerenteDao
import com.example.imparkapk.data.dao.local.dao.entity.GerenteEntity
import com.example.imparkapk.data.dao.model.Gerente
import com.example.imparkapk.data.dao.remote.api.api.usuarios.GerenteApi
import com.example.imparkapk.data.dao.remote.api.response.ApiResponse
import com.example.imparkapk.data.dao.remote.api.response.GerenteResponse
import kotlinx.coroutines.delay
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GerenteRepositoryImpl @Inject constructor(
    private val gerenteDao: GerenteDao,
    private val gerenteApi: GerenteApi
) : GerenteRepository {

    private val gerenteCache = mutableListOf<Gerente>()

    companion object {
        const val NIVEL_DONO = 1
        const val NIVEL_GERENTE = 2
        const val NIVEL_SUPERVISOR = 3
        const val NIVEL_FUNCIONARIO = 4

        val PERMISSOES_GERENTE = listOf(
            "gerenciar_estacionamento",
            "gerenciar_funcionarios",
            "visualizar_relatorios",
            "gerenciar_reservas",
            "configurar_precos"
        )
    }

    init {
        gerenteCache.addAll(
            listOf(
                Gerente(
                    id = "1",
                    usuarioId = "1",
                    nivelAcesso = NIVEL_GERENTE,
                    nome = "Gerente Principal",
                    email = "gerente@email.com",
                    senha = "senha123",
                    estacionamentoId = "1",
                    cpf = "12345678901",
                    telefone = "(11) 99999-9999",
                    dataCriacao = Date(),
                    dataAtualizacao = Date(),
                    ativo = true
                ),
                Gerente(
                    id = "2",
                    usuarioId = "2",
                    estacionamentoId = "1",
                    nivelAcesso = NIVEL_SUPERVISOR,
                    nome = "Supervisor",
                    email = "supervisor@email.com",
                    senha = "senha123",
                    cpf = "12345678902",
                    telefone = "(11) 99999-9998",
                    dataCriacao = Date(),
                    dataAtualizacao = Date(),
                    ativo = true
                ),
                Gerente(
                    id = "3",
                    usuarioId = "3",
                    estacionamentoId = "1",
                    nivelAcesso = NIVEL_FUNCIONARIO,
                    nome = "Funcionário",
                    email = "funcionario@email.com",
                    senha = "senha123",
                    cpf = "12345678903",
                    telefone = "(11) 99999-9997",
                    dataCriacao = Date(),
                    dataAtualizacao =Date(),
                    ativo = true
                )
            )
        )
    }

    override suspend fun cadastrarGerente(gerente: Gerente): Boolean {
        return try {
            delay(2000)
            val jaEGerente = gerenteCache.any {
                it.usuarioId == gerente.usuarioId && it.estacionamentoId == gerente.estacionamentoId
            }
            if (jaEGerente) {
                return false
            }
            if (gerente.nivelAcesso == NIVEL_GERENTE) {
                val jaTemGerentePrincipal = gerenteCache.any {
                    it.estacionamentoId == gerente.estacionamentoId &&
                            it.nivelAcesso == NIVEL_GERENTE
                }
                if (jaTemGerentePrincipal) {
                    return false
                }
            }
            val novoGerente = gerente.copy(
                id = UUID.randomUUID().toString(),
                dataCriacao = Date(),
                dataAtualizacao = Date()
            )
            gerenteCache.add(novoGerente)

            // Salvar no banco local
            saveToLocalCache(novoGerente)

            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getGerente(id: String): Result<Gerente?> {
        return try {
            // Buscar do cache em memória primeiro
            val gerenteCache = gerenteCache.find { it.id == id }
            if (gerenteCache != null) {
                return Result.success(gerenteCache)
            }

            // Buscar do banco local
            val entity = gerenteDao.getGerenteById(id)
            if (entity != null) {
                val gerente = mapEntityToGerente(entity)
                saveToMemoryCache(gerente)
                return Result.success(gerente)
            }

            // Buscar da API - CORREÇÃO: Adicionar return
            val response = gerenteApi.getGerente(id)
            if (response.isSuccessful && response.body() != null) {
                val gerenteResponse = response.body()!!
                val gerente = mapResponseToGerente(gerenteResponse)
                saveToLocalCache(gerente)
                saveToMemoryCache(gerente)
                return Result.success(gerente)
            } else {
                return Result.success(null)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getGerentePorUsuarioEEstacionamento(usuarioId: String, estacionamentoId: String): Result<Gerente?> {
        return try {
            val gerente = gerenteCache.find {
                it.usuarioId == usuarioId && it.estacionamentoId == estacionamentoId && it.ativo
            }
            Result.success(gerente)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun listarGerentesPorEstacionamento(estacionamentoId: String): List<Gerente> {
        return gerenteCache.filter { it.estacionamentoId == estacionamentoId && it.ativo }
    }

    override suspend fun listarGerentesPorUsuario(usuarioId: String): List<Gerente> {
        return gerenteCache.filter { it.usuarioId == usuarioId && it.ativo }
    }

    override suspend fun atualizarGerente(gerente: Gerente): Boolean {
        return try {
            val index = gerenteCache.indexOfFirst { it.id == gerente.id }
            if (index != -1) {
                val gerenteAtualizado = gerente.copy(dataAtualizacao = Date())
                gerenteCache[index] = gerenteAtualizado

                // Atualizar no banco local
                val entity = GerenteEntity(
                    id = gerenteAtualizado.id,
                    nome = gerenteAtualizado.nome,
                    email = gerenteAtualizado.email,
                    senha = gerenteAtualizado.senha,
                    usuarioId = gerenteAtualizado.usuarioId,
                    estacionamentoId = gerenteAtualizado.estacionamentoId,
                    cpf = gerenteAtualizado.cpf,
                    telefone = gerenteAtualizado.telefone,
                    dataCriacao = gerenteAtualizado.dataCriacao,
                    dataAtualizacao = gerenteAtualizado.dataAtualizacao,
                    ativo = gerenteAtualizado.ativo,
                    nivelAcesso = gerenteAtualizado.nivelAcesso
                )
                gerenteDao.updateGerente(entity)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun deletarGerente(gerenteId: String): Boolean {
        return try {
            val gerente = gerenteCache.find { it.id == gerenteId }
            if (gerente != null) {
                // Soft delete - marcar como inativo
                val gerenteAtualizado = gerente.copy(
                    ativo = false,
                    dataAtualizacao =Date()
                )
                atualizarGerente(gerenteAtualizado)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun atualizarNivelAcesso(gerenteId: String, novoNivel: Int): Boolean {
        return try {
            val index = gerenteCache.indexOfFirst { it.id == gerenteId }
            if (index != -1) {
                val gerente = gerenteCache[index]

                // Verificar se já existe gerente principal
                if (novoNivel == NIVEL_GERENTE) {
                    val jaTemGerentePrincipal = gerenteCache.any {
                        it.estacionamentoId == gerente.estacionamentoId &&
                                it.id != gerenteId &&
                                it.nivelAcesso == NIVEL_GERENTE &&
                                it.ativo
                    }
                    if (jaTemGerentePrincipal) {
                        return false
                    }
                }

                val gerenteAtualizado = gerente.copy(
                    nivelAcesso = novoNivel,
                    dataAtualizacao = Date()
                )
                gerenteCache[index] = gerenteAtualizado

                // Atualizar no banco local
                val entity = GerenteEntity(
                    id = gerenteAtualizado.id,
                    nome = gerenteAtualizado.nome,
                    email = gerenteAtualizado.email,
                    senha = gerenteAtualizado.senha,
                    usuarioId = gerenteAtualizado.usuarioId,
                    estacionamentoId = gerenteAtualizado.estacionamentoId,
                    cpf = gerenteAtualizado.cpf,
                    telefone = gerenteAtualizado.telefone,
                    dataCriacao = gerenteAtualizado.dataCriacao,
                    dataAtualizacao = gerenteAtualizado.dataAtualizacao,
                    ativo = gerenteAtualizado.ativo,
                    nivelAcesso = gerenteAtualizado.nivelAcesso
                )
                gerenteDao.updateGerente(entity)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun ativarGerente(gerenteId: String): Boolean {
        return try {
            val index = gerenteCache.indexOfFirst { it.id == gerenteId }
            if (index != -1) {
                val gerenteAtualizado = gerenteCache[index].copy(
                    ativo = true,
                    dataAtualizacao = Date()
                )
                gerenteCache[index] = gerenteAtualizado
                atualizarGerente(gerenteAtualizado)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun desativarGerente(gerenteId: String): Boolean {
        return try {
            val index = gerenteCache.indexOfFirst { it.id == gerenteId }
            if (index != -1) {
                val gerenteAtualizado = gerenteCache[index].copy(
                    ativo = false,
                    dataAtualizacao = Date()
                )
                gerenteCache[index] = gerenteAtualizado

                // Atualizar no banco local
                val entity = GerenteEntity(
                    id = gerenteAtualizado.id,
                    nome = gerenteAtualizado.nome,
                    email = gerenteAtualizado.email,
                    senha = gerenteAtualizado.senha,
                    usuarioId = gerenteAtualizado.usuarioId,
                    estacionamentoId = gerenteAtualizado.estacionamentoId,
                    cpf = gerenteAtualizado.cpf,
                    telefone = gerenteAtualizado.telefone,
                    dataCriacao = gerenteAtualizado.dataCriacao,
                    dataAtualizacao = gerenteAtualizado.dataAtualizacao,
                    ativo = gerenteAtualizado.ativo,
                    nivelAcesso = gerenteAtualizado.nivelAcesso
                )
                gerenteDao.updateGerente(entity)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun verificarPermissaoGerente(
        usuarioId: String,
        estacionamentoId: String,
        acao: String
    ): Boolean {
        return try {
            val gerente = gerenteCache.find {
                it.usuarioId == usuarioId &&
                        it.estacionamentoId == estacionamentoId &&
                        it.ativo
            }

            gerente?.let { geren ->
                val permissoesDoNivel = getPermissoesPorNivel(geren.nivelAcesso)
                permissoesDoNivel.contains(acao)
            } ?: false
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun isGerentePrincipal(gerenteId: String): Boolean {
        return try {
            val gerente = gerenteCache.find { it.id == gerenteId }
            gerente?.let {
                it.nivelAcesso == NIVEL_GERENTE && it.ativo
            } ?: false
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun isSupervisor(gerenteId: String): Boolean {
        return try {
            val gerente = gerenteCache.find { it.id == gerenteId }
            gerente?.let {
                it.nivelAcesso == NIVEL_SUPERVISOR && it.ativo
            } ?: false
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun isFuncionario(gerenteId: String): Boolean {
        return try {
            val gerente = gerenteCache.find { it.id == gerenteId }
            gerente?.let {
                it.nivelAcesso == NIVEL_FUNCIONARIO && it.ativo
            } ?: false
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun countGerentesPorEstacionamento(estacionamentoId: String): Int {
        return try {
            gerenteCache.count {
                it.estacionamentoId == estacionamentoId && it.ativo
            }
        } catch (e: Exception) {
            0
        }
    }

    override suspend fun countGerentesPorNivel(
        estacionamentoId: String,
        nivel: Int
    ): Int {
        return try {
            gerenteCache.count {
                it.estacionamentoId == estacionamentoId &&
                        it.nivelAcesso == nivel &&
                        it.ativo
            }
        } catch (e: Exception) {
            0
        }
    }

    override suspend fun getHierarquiaEstacionamento(estacionamentoId: String): Map<Int, List<Gerente>> {
        return try {
            gerenteCache
                .filter { it.estacionamentoId == estacionamentoId && it.ativo }
                .groupBy { it.nivelAcesso }
                .toSortedMap()
        } catch (e: Exception) {
            emptyMap()
        }
    }

    override suspend fun getPermissoesPorNivel(nivel: Int): List<String> {
        return when (nivel) {
            NIVEL_DONO -> listOf(
                "administrar_sistema",
                "gerenciar_estacionamento",
                "gerenciar_funcionarios",
                "visualizar_relatorios",
                "gerenciar_reservas",
                "configurar_precos",
                "definir_niveis_acesso",
                "gerenciar_financeiro",
                "configurar_horarios",
                "acesso_total"
            )

            NIVEL_GERENTE -> listOf(
                "gerenciar_estacionamento",
                "gerenciar_funcionarios",
                "visualizar_relatorios",
                "gerenciar_reservas",
                "configurar_precos",
                "gerenciar_financeiro",
                "configurar_horarios"
            )

            NIVEL_SUPERVISOR -> listOf(
                "visualizar_relatorios",
                "gerenciar_reservas",
                "configurar_precos",
                "gerenciar_veiculos"
            )

            NIVEL_FUNCIONARIO -> listOf(
                "gerenciar_reservas",
                "registrar_entrada_saida",
                "gerenciar_veiculos"
            )

            else -> emptyList()
        }
    }

    override suspend fun atualizarPermissoesGerente(
        gerenteId: String,
        permissoes: List<String>
    ): Boolean {
        return try {
            println("Permissões atualizadas para gerente $gerenteId: $permissoes")

            val gerente = gerenteCache.find { it.id == gerenteId }
            gerente?.let { geren ->
                val permissoesValidasParaNivel = getPermissoesPorNivel(geren.nivelAcesso)
                val permissoesInvalidas = permissoes - permissoesValidasParaNivel.toSet()

                if (permissoesInvalidas.isNotEmpty()) {
                    println("Atenção: Permissões inválidas para o nível ${geren.nivelAcesso}: $permissoesInvalidas")
                }
            }

            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getNivelAcesso(
        gerenteId: String,
        permissoesRequeridas: List<String>
    ): Boolean {
        return try {
            val gerente = gerenteCache.find { it.id == gerenteId && it.ativo }
            gerente?.let { geren ->
                val permissoesDoGerente = getPermissoesPorNivel(geren.nivelAcesso)
                permissoesRequeridas.all { permissao -> permissoesDoGerente.contains(permissao) }
            } ?: false
        } catch (e: Exception) {
            false
        }
    }

    // Métodos auxiliares privados
    private fun mapResponseToGerente(response: ApiResponse<GerenteResponse>): Gerente {
        val gerenteResponse = response.data ?: throw IllegalArgumentException("Response data is null")

        return Gerente(
            id = gerenteResponse.id ?: "",
            nome = gerenteResponse.nome ?: "",
            email = gerenteResponse.email ?: "",
            senha = "", // Por segurança
            usuarioId = gerenteResponse.usuarioId ?: "",
            estacionamentoId = gerenteResponse.estacionamentoId ?: "",
            cpf = gerenteResponse.cpf ?: "",
            telefone = gerenteResponse.telefone ?: "",
            dataCriacao = gerenteResponse.dataCriacao ?: Date(),
            dataAtualizacao = gerenteResponse.dataAtualizacao ?: Date() ,
            ativo = gerenteResponse.ativo ?: true,
            nivelAcesso = gerenteResponse.nivelAcesso ?: NIVEL_FUNCIONARIO
        )
    }

    private fun mapEntityToGerente(entity: GerenteEntity): Gerente {
        return Gerente(
            id = entity.id,
            nome = entity.nome,
            email = entity.email,
            senha = entity.senha,
            usuarioId = entity.usuarioId,
            estacionamentoId = entity.estacionamentoId,
            cpf = entity.cpf,
            telefone = entity.telefone,
            dataCriacao = entity.dataCriacao,
            dataAtualizacao = entity.dataAtualizacao,
            ativo = entity.ativo,
            nivelAcesso = entity.nivelAcesso
        )
    }

    private suspend fun saveToLocalCache(gerente: Gerente) {
        try {
            val entity = GerenteEntity(
                id = gerente.id,
                nome = gerente.nome,
                email = gerente.email,
                senha = gerente.senha,
                usuarioId = gerente.usuarioId,
                estacionamentoId = gerente.estacionamentoId,
                cpf = gerente.cpf,
                telefone = gerente.telefone,
                dataCriacao = gerente.dataCriacao,
                dataAtualizacao = gerente.dataAtualizacao,
                ativo = gerente.ativo,
                nivelAcesso = gerente.nivelAcesso
            )
            gerenteDao.upsertGerente(entity)
        } catch (e: Exception) {
            println("Erro ao salvar no cache local: ${e.message}")
        }
    }

    private fun saveToMemoryCache(gerente: Gerente) {
        val index = gerenteCache.indexOfFirst { it.id == gerente.id }
        if (index != -1) {
            gerenteCache[index] = gerente
        } else {
            gerenteCache.add(gerente)
        }
    }

    // Métodos adicionais da interface
    override suspend fun transferirGerencia(
        gerenteAtualId: String,
        novoGerenteId: String
    ): Boolean {
        return try {
            val gerenteAtual = gerenteCache.find { it.id == gerenteAtualId }
            val novoGerente = gerenteCache.find { it.id == novoGerenteId }

            if (gerenteAtual != null && novoGerente != null &&
                gerenteAtual.estacionamentoId == novoGerente.estacionamentoId &&
                isGerentePrincipal(gerenteAtualId)
            ) {
                atualizarNivelAcesso(gerenteAtualId, NIVEL_SUPERVISOR)
                atualizarNivelAcesso(novoGerenteId, NIVEL_GERENTE)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun promoverGerente(gerenteId: String): Boolean {
        return try {
            val gerente = gerenteCache.find { it.id == gerenteId && it.ativo }
            gerente?.let { geren ->
                when (geren.nivelAcesso) {
                    NIVEL_FUNCIONARIO -> atualizarNivelAcesso(gerenteId, NIVEL_SUPERVISOR)
                    NIVEL_SUPERVISOR -> atualizarNivelAcesso(gerenteId, NIVEL_GERENTE)
                    else -> false
                }
            } ?: false
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun rebaixarGerente(gerenteId: String): Boolean {
        return try {
            val gerente = gerenteCache.find { it.id == gerenteId && it.ativo }
            gerente?.let { geren ->
                when (geren.nivelAcesso) {
                    NIVEL_GERENTE -> {
                        val outrosGerentes = gerenteCache.count {
                            it.estacionamentoId == geren.estacionamentoId &&
                                    it.id != gerenteId &&
                                    it.nivelAcesso == NIVEL_GERENTE &&
                                    it.ativo
                        }
                        if (outrosGerentes > 0) {
                            atualizarNivelAcesso(gerenteId, NIVEL_SUPERVISOR)
                        } else {
                            false
                        }
                    }

                    NIVEL_SUPERVISOR -> atualizarNivelAcesso(gerenteId, NIVEL_FUNCIONARIO)
                    else -> false
                }
            } ?: false
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getGerentePrincipalEstacionamento(estacionamentoId: String): Gerente? {
        return try {
            gerenteCache.find {
                it.estacionamentoId == estacionamentoId &&
                        it.nivelAcesso == NIVEL_GERENTE &&
                        it.ativo
            }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getSupervisoresEstacionamento(estacionamentoId: String): List<Gerente> {
        return try {
            gerenteCache.filter {
                it.estacionamentoId == estacionamentoId &&
                        it.nivelAcesso == NIVEL_SUPERVISOR &&
                        it.ativo
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getFuncionariosEstacionamento(estacionamentoId: String): List<Gerente> {
        return try {
            gerenteCache.filter {
                it.estacionamentoId == estacionamentoId &&
                        it.nivelAcesso == NIVEL_FUNCIONARIO &&
                        it.ativo
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun verificarSePodeGerenciarEstacionamento(
        usuarioId: String,
        estacionamentoId: String
    ): Boolean {
        return try {
            val gerente = gerenteCache.find {
                it.usuarioId == usuarioId &&
                        it.estacionamentoId == estacionamentoId &&
                        it.ativo
            }
            gerente?.let {
                it.nivelAcesso in listOf(NIVEL_DONO, NIVEL_GERENTE, NIVEL_SUPERVISOR)
            } ?: false
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getNivelAcessoGerente(gerenteId: String): Int? {
        return try {
            gerenteCache.find { it.id == gerenteId && it.ativo }?.nivelAcesso
        } catch (e: Exception) {
            null
        }
    }
}