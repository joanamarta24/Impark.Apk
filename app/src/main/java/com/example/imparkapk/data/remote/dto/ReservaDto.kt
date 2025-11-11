package com.example.imparkapk.data.remote.dto

import java.sql.Time
import java.util.Date

data class ReservaDto(
    val id: Long,
    val usuarioId: String,
    val carroId: String,
    val estacionamentoId: String,
    val dataReserva: Date,
    val horaEntrada: Time,
    val horaSaida: Time,
    val valorTotal: Double,
    val status: String,
    val ativo: Boolean = true
)
