package com.example.imparkapk.data.dao.remote.api.repository.avaliacao

import com.example.imparkapk.data.dao.local.dao.dao.AvaliacaoDao
import com.example.imparkapk.data.dao.local.dao.entity.AvaliacaoEntity
import com.example.imparkapk.data.dao.model.Avaliacao
import com.example.imparkapk.data.dao.remote.api.api.AvaliacaoApi
import com.example.imparkapk.data.dao.remote.api.repository.reserva.ReservaRepository
import com.example.imparkapk.data.dao.remote.api.request.AvaliacaoRequest
import com.example.imparkapk.domain.util.Result
import kotlinx.coroutines.delay
import java.util.Calendar
import java.util.Date
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AvaliacaoRepositoryImpl @Inject constructor(
    private val avaliacaoDao: AvaliacaoDao,
    private val avaliacaoApi: AvaliacaoApi,
    private val reservaRepository: ReservaRepository
) : AvaliacaoRepository {
    private val avaliacoesCache = mutableListOf<Avaliacao>()

    init {
        val calendario = Calendar.getInstance()
        calendario.add(Calendar.DAY_OF_YEAR, -7)

        avaliacoesCache.addAll(
            listOf(
                Avaliacao(
                    id = "1",
                    usuarioId = "1",
                    estacionamentoId = "1",
                    nota = 5,
                    comentario = "Excelente estacionamento! Atendimento rápido e vagas amplas. Recomendo",
                    dataAvaliacao = Date()
                ),
                Avaliacao(
                    id = "2",
                    usuarioId = "2",
                    estacionamentoId = "1",
                    nota = 4,
                    comentario = "Bom estacionamento, mas poderia ter mais sinalização interna.",
                    dataAvaliacao = calendario.time
                ),
                Avaliacao(
                    id = "3",
                    usuarioId = "3",
                    estacionamentoId = "1",
                    nota = 3,
                    comentario = "Preço um pouco alto para a localização. Vagas um pouco apertadas.",
                    dataAvaliacao = calendario.time
                ),
                Avaliacao(
                    id = "4",
                    usuarioId = "1",
                    estacionamentoId = "2",
                    nota = 5,
                    comentario = "Ótima estrutura e atendimento. Estacionamento muito organizado!",
                    dataAvaliacao = Date()
                ),
                Avaliacao(
                    id = "5",
                    usuarioId = "2",
                    estacionamentoId = "2",
                    nota = 4,
                    comentario = "Boa localização e preço justo. Faltam mais vagas para idosos.",
                    dataAvaliacao = calendario.time
                )
            )
        )
    }

    override suspend fun criarAvaliacao(avaliacao: Avaliacao): Result<Avaliacao> {
        return try {
            delay(1500)

            // Verifica se usuário já avaliou este estacionamento
            val jaAvaliou = avaliacoesCache.any {
                it.usuarioId == avaliacao.usuarioId &&
                        it.estacionamentoId == avaliacao.estacionamentoId
            }

            if (jaAvaliou) {
                return Result.failure(Exception("Usuário já avaliou este estacionamento"))
            }

            // Verifica se usuário pode avaliar (teve reserva neste estacionamento)
            val podeAvaliar = verificarPodeAvaliar(avaliacao.usuarioId, avaliacao.estacionamentoId)

            if (!podeAvaliar) {
                return Result.failure(Exception("Usuário não pode avaliar este estacionamento"))
            }

            val novaAvaliacao = avaliacao.copy(
                id = UUID.randomUUID().toString(),
                dataAvaliacao = Date()
            )

            avaliacoesCache.add(novaAvaliacao)

            // Salvar no banco local
            avaliacaoDao.inserirAvaliacao(novaAvaliacao.toEntity())

            Result.success(novaAvaliacao)

        } catch (e: Exception) {
            Result.failure(Exception("Erro ao criar avaliação: ${e.message}"))
        }
    }

    override suspend fun getAvaliacaoPorId(id: String): Result<Avaliacao?> {
        return try {
            // Primeiro tenta da API
            val response = avaliacaoApi.getAvaliacao(id)
            if (response.isSuccessful && response.body() != null) {
                val avaliacaoResponse = response.body()!!
                val avaliacao = fromResponse(avaliacaoResponse)

                // Atualiza cache
                val index = avaliacoesCache.indexOfFirst { it.id == id }
                if (index != -1) {
                    avaliacoesCache[index] = avaliacao
                } else {
                    avaliacoesCache.add(avaliacao)
                }

                Result.success(avaliacao)
            } else {
                // Fallback: busca do cache
                val avaliacaoCacheEncontrada = avaliacoesCache.find { it.id == id }
                if (avaliacaoCacheEncontrada != null) {
                    Result.success(avaliacaoCacheEncontrada)
                } else {
                    Result.success(null)
                }
            }
        } catch (e: Exception) {
            // Fallback final: busca do banco local
            val entity = avaliacaoDao.getAvaliacaoById(id)
            if (entity != null) {
                val avaliacao = fromEntity(entity)
                Result.success(avaliacao)
            } else {
                Result.failure(Exception("Avaliação não encontrada"))
            }
        }
    }

    override suspend fun listarAvaliacoesPorEstacionamento(estacionamentoId: String): Result<List<Avaliacao>> {
        return try {
            delay(1200)
            val avaliacoes = avaliacoesCache
                .filter { it.estacionamentoId == estacionamentoId }
                .sortedByDescending { it.dataAvaliacao }
            Result.success(avaliacoes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun listarMinhasAvaliacoes(usuarioId: String): Result<List<Avaliacao>> {
        return try {
            delay(1000)
            val avaliacoes = avaliacoesCache
                .filter { it.usuarioId == usuarioId }
                .sortedByDescending { it.dataAvaliacao }
            Result.success(avaliacoes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun atualizarAvaliacao(avaliacao: Avaliacao): Result<Boolean> {
        return try {
            delay(1200)
            val index = avaliacoesCache.indexOfFirst { it.id == avaliacao.id }
            if (index != -1) {
                val avaliacaoAtualizada = avaliacao.copy(dataAvaliacao = Date())
                avaliacoesCache[index] = avaliacaoAtualizada

                // Atualizar na API
                avaliacaoApi.atualizarAvaliacao(avaliacao.id, avaliacao.toRequest())

                // Atualizar no banco local
                avaliacaoDao.atualizarAvaliacao(avaliacaoAtualizada.toEntity())

                Result.success(true)
            } else {
                Result.success(false)
            }
        } catch (e: Exception) {
            Result.failure(Exception("Erro ao atualizar avaliação: ${e.message}"))
        }
    }

    override suspend fun deletarAvaliacao(avaliacaoId: String): Result<Boolean> {
        return try {
            delay(800)
            // CORREÇÃO: estava usando = em vez de ==
            val removed = avaliacoesCache.removeAll { it.id == avaliacaoId }

            // Deletar da API
            avaliacaoApi.deletarAvaliacao(avaliacaoId)

            // Deletar do banco local
            avaliacaoDao.deletarAvaliacao(avaliacaoId)

            Result.success(removed)
        } catch (e: Exception) {
            Result.failure(Exception("Erro ao deletar avaliação: ${e.message}"))
        }
    }

    override suspend fun sincronizarAvaliacoes(): Result<Boolean> {
        return try {
            delay(2000)
            // Busca todas as avaliações da API
            val response = avaliacaoApi.getAvaliacoes()
            if (response.isSuccessful && response.body() != null) {
                val avaliacoesResponse = response.body()!!
                val avaliacoes = avaliacoesResponse.data.map { fromResponse(it) }

                // Atualiza cache
                avaliacoesCache.clear()
                avaliacoesCache.addAll(avaliacoes)

                // Atualiza banco local
                val entities = avaliacoes.map { it.toEntity() }
                avaliacaoDao.inserirAvaliacoesEmLote(entities)

                Result.success(true)
            } else {
                Result.success(false)
            }
        } catch (e: Exception) {
            Result.failure(Exception("Erro na sincronização: ${e.message}"))
        }
    }

    override suspend fun calcularMediaAvaliacoes(estacionamentoId: String): Result<Double> {
        return try {
            delay(600)
            val avaliacoes = avaliacoesCache.filter { it.estacionamentoId == estacionamentoId }
            val media = if (avaliacoes.isNotEmpty()) {
                avaliacoes.map { it.nota.toDouble() }.average()
            } else {
                0.0
            }
            Result.success(media)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun countAvaliacoesPorEstacionamento(estacionamentoId: String): Result<Int> {
        return try {
            delay(300)
            val count = avaliacoesCache.count { it.estacionamentoId == estacionamentoId }
            Result.success(count)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun countMinhasAvaliacoes(usuarioId: String): Result<Int> {
        return try {
            delay(300)
            val count = avaliacoesCache.count { it.usuarioId == usuarioId }
            Result.success(count)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAvaliacoesPorUsuarioEEstacionamento(
        usuarioId: String,
        estacionamentoId: String
    ): Result<Avaliacao?> {
        return try {
            delay(500)
            val avaliacao = avaliacoesCache.find {
                it.usuarioId == usuarioId &&
                        it.estacionamentoId == estacionamentoId
            }
            Result.success(avaliacao)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun verificarPodeAvaliar(
        usuarioId: String,
        estacionamentoId: String
    ): Boolean {
        return try {
            delay(800)

            // Verifica se usuário já avaliou
            val jaAvaliou = avaliacoesCache.any {
                it.usuarioId == usuarioId &&
                        it.estacionamentoId == estacionamentoId
            }

            if (jaAvaliou) {
                return false
            }

            // Simulação para teste
            val teveReserva = true

            return teveReserva

        } catch (e: Exception) {
            false
        }
    }

    override fun validarNota(nota: Int): Boolean {
        return nota in 1..5
    }

    override fun validarComentario(comentario: String): Boolean {
        return comentario.length <= 500
    }

    override suspend fun getAvaliacoesRecentes(
        estacionamentoId: String,
        limite: Int
    ): Result<List<Avaliacao>> {
        return try {
            delay(700)
            val avaliacoes = avaliacoesCache
                .filter { it.estacionamentoId == estacionamentoId }
                .sortedByDescending { it.dataAvaliacao }
                .take(limite)
            Result.success(avaliacoes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getDistribuicaoNotas(estacionamentoId: String): Result<Map<Int, Int>> {
        return try {
            delay(500)
            val avaliacoes = avaliacoesCache.filter { it.estacionamentoId == estacionamentoId }
            val distribuicao = (1..5).associateWith { nota ->
                avaliacoes.count { it.nota == nota }
            }
            Result.success(distribuicao)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAvaliacoesComComentario(estacionamentoId: String): Result<List<Avaliacao>> {
        return try {
            delay(600)
            val avaliacoes = avaliacoesCache
                .filter {
                    it.estacionamentoId == estacionamentoId &&
                            it.comentario.isNotBlank()
                }
                .sortedByDescending { it.dataAvaliacao }
            Result.success(avaliacoes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun sincronizarAvaliacoes(usuarioId: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

    // Métodos auxiliares
    private fun fromResponse(response: AvaliacaoResponse): Avaliacao {
        return Avaliacao(
            id = response.id ?: "",
            usuarioId = response.usuarioId ?: "",
            estacionamentoId = response.estacionamentoId ?: "",
            nota = response.nota ?: 0,
            comentario = response.comentario ?: "",
            dataAvaliacao = response.dataAvaliacao ?: Date()
        )
    }

    private fun fromEntity(entity: AvaliacaoEntity): Avaliacao {
        return Avaliacao(
            id = entity.id,
            usuarioId = entity.usuarioId,
            estacionamentoId = entity.estacionamentoId,
            nota = entity.nota,
            comentario = entity.comentario,
            dataAvaliacao = entity.dataAvaliacao
        )
    }

    private fun Avaliacao.toEntity(): AvaliacaoEntity {
        return AvaliacaoEntity(
            id = this.id,
            usuarioId = this.usuarioId,
            estacionamentoId = this.estacionamentoId,
            nota = this.nota,
            comentario = this.comentario,
            dataAvaliacao = this.dataAvaliacao
        )
    }

    private fun Avaliacao.toRequest(): AvaliacaoRequest {
        return AvaliacaoRequest(
            usuarioId = this.usuarioId,
            estacionamentoId = this.estacionamentoId,
            nota = this.nota,
            comentario = this.comentario
        )
    }

    // Implementação do método sincronizarUsuarios (corrigido)
    override suspend fun sincronizarUsuarios() {
        // Este método parece não fazer sentido aqui,
        // pois avaliações não sincronizam usuários.
        // Talvez seja um método herdado incorretamente.
        // Vamos implementar como no-op ou remover da interface
    }
}