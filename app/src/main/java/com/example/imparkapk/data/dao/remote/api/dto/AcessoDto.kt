package com.example.imparkapk.data.dao.remote.api.dto

import java.time.LocalDateTime

data class AcessoDto(
    val id: Long? = null,
    val veiculoId: Long,
    val veiculoPlaca: String? = null,
    val estacionamentoId: Long,
    val estacionamentoNome: String? = null,
    val dataEntrada: LocalDateTime,
    val dataSaida: LocalDateTime? = null,
    val tipoAcesso: String, // "ENTRADA" ou "SAIDA"
    val valorCobrado: Double? = null,
    val duracaoMinutos: Long? = null,
    val usuarioRegistroId: Long? = null,
    val observacoes: String? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
)
