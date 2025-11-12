package com.example.imparkapk.domain.model

import com.example.imparkapk.domain.model.usuarios.Cliente
import java.sql.Time
import java.util.Date

data class Reserva(
    val id: Long,
    val cliente: Cliente?,
    val carro: Carro?,
    val estacionamento: Estacionamento?,
    val dataReserva: Date,
    val horaEntrada: Time,
    val horaSaida: Time,
    val valorTotal: Double,
    val status: String,
    val ativo: Boolean = true
)