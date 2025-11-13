package com.example.imparkapk.data.dao.remote.api.repository.avaliacao

import com.example.imparkapk.data.dao.local.dao.dao.AvaliacaoDao
import com.example.imparkapk.data.dao.model.Avaliacao
import com.example.imparkapk.data.dao.remote.api.api.AvaliacaoApi
import com.example.imparkapk.data.dao.remote.api.repository.reserva.ReservaRepository
import kotlinx.coroutines.delay
import java.util.Calendar
import java.util.Date
import java.util.UUID
import javax.inject.Inject

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

    override suspend fun criarAvaliacao(avaliacao: Avaliacao): Boolean {
        return try {
            delay(1500)
            // Verifica se usuário já avaliou este estacionamento
            val jaAvaliou = avaliacoesCache.any {
                it.usuarioId == avaliacao.usuarioId &&
                        it.estacionamentoId == avaliacao.estacionamentoId
            }
            if (jaAvaliou) {
                return false
            }
            // Verifica se usuário pode avaliar (teve reserva neste estacionamento)
            val podeAvaliar = verificarPodeAvaliar(avaliacao.usuarioId, avaliacao.estacionamentoId)
            if (!podeAvaliar) {
                return false
            }

            val novaAvaliacao = avaliacao.copy(
                id = UUID.randomUUID().toString(),
                dataAvaliacao = Date()
            )
            avaliacoesCache.add(novaAvaliacao)
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getAvaliacaoPorId(id: String): Result<Avaliacao?> {
        return try {
            val avaliacao = avaliacoesCache.find { it.id == id }
            Result.success(avaliacao)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun listarAvaliacoesPorEstacionamento(estacionamentoId: String): List<Avaliacao> {
        delay(1200)
        return avaliacoesCache
            .filter { it.estacionamentoId == estacionamentoId }
            .sortedByDescending { it.dataAvaliacao }
    }

    override suspend fun listarMinhasAvaliacoes(usuarioId: String): List<Avaliacao> {
        delay(1000)
        return avaliacoesCache
            .filter { it.usuarioId == usuarioId }
            .sortedByDescending { it.dataAvaliacao }
    }

    override suspend fun atualizarAvaliacao(avaliacao: Avaliacao): Boolean {
        return try {
            delay(1200)
            val index = avaliacoesCache.indexOfFirst { it.id == avaliacao.id }
            if (index != -1) {
                avaliacoesCache[index] = avaliacao.copy(dataAvaliacao = Date())
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun deletarAvaliacao(avaliacaoId: String): Boolean {
        return try {
            delay(800)
            val removed = avaliacoesCache.removeAll { it.id == avaliacaoId }
            removed
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun calcularMediaAvaliacoes(estacionamentoId: String): Result<Double> {
        return try {
            delay(600)
            val avaliacoes = avaliacoesCache.filter { it.estacionamentoId == estacionamentoId }
            val media = if (avaliacoes.isNotEmpty()) {
                avaliacoes.map { it.nota }.average()
            } else {
                0.0
            }
            Result.success(media)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun countAvaliacoesPorEstacionamento(estacionamentoId: String): Int {
        delay(300)
        return avaliacoesCache.count { it.estacionamentoId == estacionamentoId }
    }

    override suspend fun countMinhasAvaliacoes(usuarioId: String): Int {
        delay(300)
        return avaliacoesCache.count { it.usuarioId == usuarioId }
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
        delay(800)
        // Verifica se usuário já avaliou
        val jaAvaliou = avaliacoesCache.any {
            it.usuarioId == usuarioId &&
                    it.estacionamentoId == estacionamentoId
        }
        if (jaAvaliou) {
            return false
        }
        // Verifica se usuário teve pelo menos uma reserva neste estacionamento
        val teveReserva = true // reservaRepository.temReservaNoEstacionamento(usuarioId, estacionamentoId)

        return teveReserva
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
    ): List<Avaliacao> {
        delay(700)
        return avaliacoesCache
            .filter { it.estacionamentoId == estacionamentoId }
            .sortedByDescending { it.dataAvaliacao }
            .take(limite)
    }

    override suspend fun getDistribuicaoNotas(estacionamentoId: String): Map<Int, Int> {
        delay(500)
        val avaliacoes = avaliacoesCache.filter { it.estacionamentoId == estacionamentoId }
        return (1..5).associateWith { nota ->
            avaliacoes.count { it.nota == nota }
        }
    }

    override suspend fun getAvaliacoesComComentario(estacionamentoId: String): List<Avaliacao> {
        delay(600)
        return avaliacoesCache
            .filter {
                it.estacionamentoId == estacionamentoId &&
                        it.comentario.isNotBlank()
            }
            .sortedByDescending { it.dataAvaliacao }
    }

    override suspend fun sincronizarAvaliacoes(usuarioId: String): Result<Boolean> {
        return try {
            delay(2000)
            // Simula sincronização com servidor
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Métodos auxiliares para análise de sentimentos
    suspend fun analisarSentimentoAvaliacoes(estacionamentoId: String): Map<String, Any> {
        delay(1000)
        val avaliacoes = avaliacoesCache.filter { it.estacionamentoId == estacionamentoId }
        val comentarios = avaliacoes.mapNotNull { it.comentario }

        // Simulação simples de análise de sentimentos
        val palavrasPositivas = listOf("excelente", "ótimo", "bom", "recomendo", "ótima")
        val palavrasNegativas = listOf("ruim", "péssimo", "horrível", "problema", "falta")

        var sentimentoPositivo = 0
        var sentimentoNegativo = 0
        var sentimentoNeutro = 0

        comentarios.forEach { comentario ->
            val palavras = comentario.lowercase().split(" ")
            val positivas = palavras.count { it in palavrasPositivas }
            val negativas = palavras.count { it in palavrasNegativas }

            when {
                positivas > negativas -> sentimentoPositivo++
                negativas > positivas -> sentimentoNegativo++
                else -> sentimentoNeutro++
            }
        }

        return mapOf(
            "total_avaliacoes" to avaliacoes.size,
            "sentimento_positivo" to sentimentoPositivo,
            "sentimento_negativo" to sentimentoNegativo,
            "sentimento_neutro" to sentimentoNeutro,
            "palavras_chave" to extrairPalavrasChave(comentarios)
        )
    }

    private fun extrairPalavrasChave(comentarios: List<String>): List<String> {
        val todasPalavras = comentarios.flatMap { it.lowercase().split(" ") }
        val palavrasFrequentes = todasPalavras
            .filter { it.length > 3 }
            .groupingBy { it }
            .eachCount()
            .entries
            .sortedByDescending { it.value }
            .take(10)
            .map { it.key }

        return palavrasFrequentes
    }
}