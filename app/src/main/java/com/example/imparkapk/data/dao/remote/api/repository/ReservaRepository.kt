package com.example.imparkapk.data.dao.remote.api.repository

import com.example.imparkapk.data.dao.model.Reserva
import kotlinx.coroutines.flow.Flow
import java.net.IDN
import java.util.Date

interface ReservaRepository {
    suspend fun criarReserva(reserva: Reserva): Boolean
    suspend fun getReservaPorId(id: String): Result<Reserva?>
    suspend fun listaReservasPorUsuario(usuarioId: String): List<Reserva>
    suspend fun listarReservasPorEstacionamento(estacionamentoId: String): List<Reserva>

    suspend fun atualizarReserva(reserva: Reserva): Boolean
    suspend fun cancelarReserva(reservaId: String): Boolean
    suspend fun confirmarReserva(reservaId: String): Boolean

    //Consultas
    suspend fun getReservasPorData(data: Date,estacionamentoId: String): List<Reserva>
    suspend fun getReservasAtivasPorUsuario(usuarioId: String): List<Reserva>
    suspend fun getReservasFuturasPorUsuario(usuarioId: String): List<Reserva>

    //Validações
    suspend fun verificarDisponibilidade(
        estacionamentoId: String,
        data: Date,
        horaEntrada: String,
        horaSaida: String
    ): Boolean
    suspend fun calcularValorReserva(estacionamentoId: String,horas: Int): Double
    suspend fun countReservasPorUsuario(usuarioId: String): Int
    suspend fun getHistoricoReservas(usuarioId: String): List<Reserva>
}
