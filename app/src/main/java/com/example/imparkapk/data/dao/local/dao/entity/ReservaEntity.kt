// ReservaEntity.kt
package com.example.imparkapk.data.dao.local.dao.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "reservas")
data class ReservaEntity(
    @PrimaryKey
    val id: String,
    val usuarioId: String,
    val carroId: String,
    val estacionamentoId: String,
    val dataReserva: Date,
    val horaEntrada: String,
    val horaSaida: String,
    val valorTotal: Double,
    val status: String,
    val codigoReserva: String,
    val dataCriacao: Date,
    val dataAtualizacao: Date
)

// ReservaResponse.kt

data class ReservaResponse(
    val id: String? = null,
    val usuarioId: String? = null,
    val carroId: String? = null,
    val estacionamentoId: String? = null,
    val dataReserva: Date? = null,
    val horaEntrada: String? = null,
    val horaSaida: String? = null,
    val valorTotal: Double? = null,
    val status: String? = null,
    val codigoReserva: String? = null,
    val dataCriacao: Date? = null,
    val dataAtualizacao: Date? = null
)

// ApiResponse.kt (para respostas paginadas)
data class ApiResponse<T>(
    val success: Boolean,
    val data: T,
    val message: String? = null
)

// ReservaRequest.kt

data class ReservaRequest(
    val usuarioId: String,
    val carroId: String,
    val estacionamentoId: String,
    val dataReserva: Date,
    val horaEntrada: String,
    val horaSaida: String,
    val status: String = "pendente"
)