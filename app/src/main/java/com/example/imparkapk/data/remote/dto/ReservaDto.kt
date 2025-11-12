package com.example.imparkapk.data.remote.dto

import java.sql.Time
import java.util.Date

data class ReservaDto(
    val id: Long,
    val usuarioId: Long?,
    val carroId: Long?,
    val estacionamentoId: Long?,
    val dataReserva: Date,
    val horaEntrada: Time,
    val horaSaida: Time,
    val valorTotal: Double,
    val status: String,
    val updatedAt: Long,
    val ativo: Boolean = true
)
