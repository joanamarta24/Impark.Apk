package com.example.imparkapk.data.dao.repository

import com.example.imparkapk.data.dao.model.Reserva
import kotlinx.coroutines.flow.Flow

interface ReservaRepository {
    suspend fun criarReserva(reserva: Reserva): Result<Boolean>
    fun getReservasPorUsuario(usuarioId: String): Flow<List<Reserva>>
    suspend fun cancelarReserva(id: String): Result<Boolean>
    suspend fun confirmarReserva(id: String): Result<Boolean>
}
