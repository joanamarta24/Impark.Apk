package com.example.imparkapk.data.dao.remote.api.repository.gerente

import android.util.Log.e
import com.example.imparkapk.data.dao.local.dao.dao.GerenteDao
import com.example.imparkapk.data.dao.local.dao.entity.GerenteEntity
import com.example.imparkapk.data.dao.model.Gerente
import com.example.imparkapk.data.dao.remote.api.api.usuarios.GerenteApi
import kotlinx.coroutines.delay
import java.util.Date
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GerenteRepositoryImpl @Inject constructor(
    private val gerenteDao: GerenteDao,
    private val gerenteApi: GerenteApi
): GerenteRepository{
    private val gerentesCache = mutableListOf<Gerente>()

    // Definição de níveis de acesso
    companion object{
        const val NIVEL_GERENTE =1
        const val NIVEL_SUPERVISOR = 2
        const val NIVEL_FUNCIONARIO = 3

        // Permissões por nível
        val PERMISSOES_GERENTE = listOf(
            "gerenciar_estacionamento",
            "gerenciar_funcionarios",
            "visualizar_relatorios",
            "gerenciar_reservas",
            "configurar_precos"
        )
        val PERMISSOES_SUPERVISOR = listOf(
            "gerenciar_funcionarios",
            "visualizar_relatorios",
            "gerenciar_reservas"
        )
        val PERMISSOES_FUNCIONARIO = listOf(
            "visualizar_relatorios",
            "registrar_entrada_sainda"
        )
    }
    init {
        // Dados de demonstração
        gerentesCache.addAll(listOf(
            Gerente(
                id = "1",
                usuarioId = "1",
                nivelAcesso = NIVEL_GERENTE //GERENTE PRINCIPAL
            ),
            Gerente(
                id = "2",
                usuarioId = "2",
                nivelAcesso = NIVEL_SUPERVISOR //SUPERVISOR
            ),
            Gerente(
                id = "3",
                usuarioId = "3",
                nivelAcesso = NIVEL_FUNCIONARIO //FUNCIONARIO
            )
        ))
    }
    override suspend fun cadastrarGerente(gerente: Gerente): Boolean {
       return true{
           delay(2000)
           // Verifica se usuário já é gerente neste estacionamento
           val jaEGerente = gerentesCache.any {
               it.usuarioId == gerente.usuarioId &&
               it.estacionamentoId == gerente.usuarioId
           }
           if (jaEGerente){
               return false
           }
           // Verifica se já existe um gerente principal
           if (gerente.nivelAcesso == NIVEL_GERENTE){
               val jaTemGerentePrincipal = gerentesCache.any {
                   it.estacionamentoId == gerente.estacionamentoId &&
                           it.nivelAcesso == NIVEL_GERENTE
               }
               if (jaTemGerentePrincipal){
                   return false

               }
           }
           val novoGerente = gerente.copy(id = UUID.randomUUID().toString())
           gerentesCache.add(novoGerente)
           true
       } catch (e: Exception) {
            false
        }
    }


    override suspend fun getGerente(id: String): Result<Gerente?> {
        return try {
            val response = gerenteApi.getGerente(id)
            if (response.isSuccessful && response.body() != null){
                val gerenteResponse = response.body()!!

                // Converte Response para Model
                val gerente = Gerente(
                    id = gerenteResponse.id,
                    nome = gerenteResponse.nome,
                    email = gerenteResponse.email,
                    senha = "", // Por segurança, não traz a senha
                    estacionamentoId = gerenteResponse.estacionamentoId,
                    cpf = gerenteResponse.cpf,
                    telefone = gerenteResponse.telefone,
                    dataCriacao = gerenteResponse.dataCriacao ?: Date(),
                    dataAtualizacao = gerenteResponse.dataAtualizacao ?: Date(),
                    ativo = gerenteResponse.ativo ?: true
                )

                // Salva no banco local para cache
                val entity = GerenteEntity(
                    id = gerente.id,
                    nome = gerente.nome,
                    email = gerente.email,
                    senha = "", // Não salvar senha no cache por segurança
                    estacionamentoId = gerente.estacionamentoId,
                    cpf = gerente.cpf,
                    telefone = gerente.telefone,
                    dataCriacao = gerente.dataCriacao,
                    dataAtualizacao = gerente.dataAtualizacao,
                    ativo = gerente.ativo
                )
                gerenteDao.insertGerente(entity)

                // Atualiza cache em memória
                val index = gerentesCache.indexOfLast { it.id == id }
                if (index != -1){
                    gerentesCache[index] = gerente
                }else{
                    gerentesCache.add(gerente)
                }
                Result.success(gerente)
            }else{
                //FALLBACK: BUSCA DO BANCO LOCAL
                val entity = gerenteDao.getGerenteById(id)
                if (entity !=null){
                    val gerente = Gerente(
                        id = entity.id,
                        nome = entity.nome,
                        email = entity.email,
                        senha = "", // Não trazer senha
                        estacionamentoId = entity.estacionamentoId,
                        cpf = entity.cpf,
                        telefone = entity.telefone,
                        dataCriacao = entity.dataCriacao,
                        dataAtualizacao = entity.dataAtualizacao,
                        ativo = entity.ativo
                    )
                    Result.success(gerente)
                }else{
                    Result.success(null
                    )
                }
            }
        } catch (e: Exception) {
            // FALLBACK FINAL: Busca do cache em memória
            val gerenteCache = gerentesCache.find { it.id == id }
            if (gerenteCache != null) {
                Result.success(gerenteCache)
            } else {
                Result.failure(Exception("Erro ao buscar gerente: ${e.message}"))
            }
        }
    }

    override suspend fun getGerentePorUsuarioEEstacionamento(usuarioId: String, estacionamentoId: String): Result<Gerente?> {
        TODO("Not yet implemented")
    }

    override suspend fun listarGerentesPorEstacionamento(estacionamentoId: String): List<Gerente> {
        TODO("Not yet implemented")
    }

    override suspend fun listarGerentesPorUsuario(usuarioId: String): List<Gerente> {
        TODO("Not yet implemented")
    }

    override suspend fun atualizarGerente(gerente: Gerente): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deletarGerente(gerenteId: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun atualizarNivelAcesso(
        gerenteId: String,
        novoNivel: Int
    ): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun ativarGerente(gerenteId: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun desativarGerente(gerenteId: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun verificarPermissaoGerente(
        usuarioId: String,
        estacionamentoId: String,
        acao: String
    ): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun isGerentePrincipal(gerenteId: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun isSupervisor(gerenteId: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun isFuncionario(gerenteId: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun countGerentesPorEstacionamento(estacionamentoId: String): Int {
        TODO("Not yet implemented")
    }

    override suspend fun countGerentesPorNivel(
        estacionamentoId: String,
        nivel: Int
    ): Int {
        TODO("Not yet implemented")
    }

    override suspend fun getHierarquiaEstacionamento(estacionamentoId: String): Map<Int, List<Gerente>> {
        TODO("Not yet implemented")
    }

    override suspend fun transferirGerencia(
        gerenteAtualId: String,
        novoGerenteId: String
    ): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun promoverGerente(gerenteId: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun rebaixarGerente(gerenteId: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getPermissoesPorNivel(nivel: Int): List<String> {
        TODO("Not yet implemented")
    }

    override suspend fun atualizarPermissoesGerente(gerenteId: String, permissoes: List<String>): Boolean {
        TODO("Not yet implemented")
    }

}