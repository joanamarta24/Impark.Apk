package com.example.imparkapk.data.dao.remote.api.repository.reserva

import com.example.imparkapk.data.dao.model.Reserva
import java.util.Date

interface ReservaRepository {
    suspend fun criarReserva(reserva: Reserva): Result<Reserva>
    suspend fun getReservaPorId(id: String): Result<Reserva?>
    suspend fun listaReservasPorUsuario(usuarioId: String): List<Reserva>
    suspend fun listarReservasPorEstacionamento(estacionamentoId: String): List<Reserva>
    suspend fun obterTodasReservas(): Result<List<Reserva>>
    suspend fun atualizarReserva(reserva: Reserva): Result<Boolean>
    suspend fun cancelarReserva(reservaId: String): Result<Boolean>
    suspend fun confirmarReserva(reservaId: String): Result<Boolean>
    suspend fun getReservasPorData(data: Date, estacionamentoId: String): Result<List<Reserva>>
    suspend fun getReservasAtivasPorUsuario(usuarioId: String): Result<List<Reserva>>
    suspend fun getReservasFuturasPorUsuario(usuarioId: String): Result<List<Reserva>>
    suspend fun verificarDisponibilidade(
        estacionamentoId: String,
        data: Date,
        horaEntrada: String,
        horaSaida: String
    ): Result<Boolean>

    suspend fun calcularValorReserva(estacionamentoId: String, horas: Int): Result<Double>
    suspend fun sincronizarReservas(): Result<Boolean>

    suspend fun countReservasPorUsuario(usuarioId: String): Result<Int>
    suspend fun getHistoricoReservas(usuarioId: String): Result<List<Reserva>>

    // MÉTODO ADICIONADO que estava faltando:
    suspend fun obterReservasPorUsuario(usuarioId: String): Result<List<Reserva>>
}
