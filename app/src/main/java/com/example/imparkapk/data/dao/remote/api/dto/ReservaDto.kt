package com.example.imparkapk.data.dao.remote.api.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class ReservaDto(
    val id: Long? = null,
    val usuarioId: Long,
    val usuarioNome: String? = null,
    val estacionamentoId: Long,
    val estacionamentoNome: String? = null,
    val veiculoId: Long,
    val veiculoPlaca: String? = null,
    val dataReserva: LocalDateTime,
    val dataEntradaPrevista: LocalDateTime,
    val dataSaidaPrevista: LocalDateTime,
    val dataEntradaReal: LocalDateTime? = null,
    val dataSaidaReal: LocalDateTime? = null,
    val status: String, // "PENDENTE", "CONFIRMADA", "EM_ANDAMENTO", "FINALIZADA", "CANCELADA"
    val valorPrevisto: BigDecimal,
    val valorFinal: BigDecimal? = null,
    val vformaPagamento: String? = null,
    val codigoReserva: String? = null,
    val vagaNumero: String? = null,
    val observacoes: String? = null,
    val motivoCancelamento: String? = null,
    val tempoToleranciaMinutos: Int? = 15,
    val isRecorrente: Boolean = false,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
)
data class AvaliacaoDto(
    val id: Long? = null,
    val estacionamentoId: Long,
    val estacionamentoNome: String? = null,
    val usuarioId: Long,
    val usuarioNome: String? = null,
    val nota: Int, // 1-5
    val comentario: String? = null,
    val titulo: String? = null,
    val dataAvaliacao: LocalDateTime,
    val isAtivo: Boolean = true,
    val likes: Int = 0,
    val dislikes: Int = 0,
    val respostaGestor: String? = null,
    val dataResposta: LocalDateTime? = null,
    val tags: List<String>? = null,
    val createdAt: LocalDateTime? = null
)
