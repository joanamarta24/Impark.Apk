package com.example.imparkapk.data.dao.remote.api.repository.reserva

import com.example.imparkapk.data.dao.model.Reserva
import com.example.imparkapk.data.dao.remote.api.api.ReservaApi
import com.example.imparkapk.data.dao.remote.api.repository.reserva.ReservaRepository
import com.example.imparkapk.data.dao.remote.api.repository.estacionamento.EstacionamentoRepository
import kotlinx.coroutines.delay
import java.util.Calendar
import java.util.Date
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReservaRepositoryImpl @Inject constructor(
    private val reservaDao: ReservaDao,
    private  val reservaApi: ReservaApi
): ReservaRepository {
    private val reservaCache = mutableListOf<Reserva>()
    private val estacionamentoRepository: EstacionamentoRepository

    @Inject
    constructor(
        reservaDao: ReservaDao,
        reservaApi: ReservaApi,
    ) : this(reservaDao, reservaApi) {
        this.estacionamentoRepository = estacionamentoRepository
    }

    init {
        //Dados de demonstração
        val calendario = Calendar.getInstance()

        //Reseva futura
        calendario.time = Date()
        calendario.add(Calendar.DAY_OF_MONTH, 1)
        val reservaFurura = calendario.time

        //Reserva passada
        calendario.time = Date()
        calendario.add(Calendar.DAY_OF_MONTH, -1)
        val reservaPassada = calendario.time

        reservaCache.addAll(
            listOf(
                Reserva(
                    id = "1",
                    usuarioId = "1",
                    carroId = "1",
                    estacionamentoId = "1",
                    dataReserva = reservaFurura,
                    horaEntrada = "14:00",
                    horaSaida = "16:00",
                    valorTotal = 17.00,
                    status = "confirmada",
                    codigoReserva = "RES12345"
                ),
                Reserva(
                    id = "2",
                    usuarioId = "1",
                    carroId = "2",
                    estacionamentoId = "2",
                    dataReserva = reservaPassada,
                    horaEntrada = "10:00",
                    horaSaida = "12:00",
                    valorTotal = 24.00,
                    status = "concluida",
                    codigoReserva = "RES67890"
                )
            )
        )
    }

    override suspend fun criarReserva(reserva: Reserva): Boolean {
        return try {
            delay(2000)

            // Verifica disponibilidade
            val disponivel = verificarDisponibilidade(
                reserva.estacionamentoId,
                reserva.dataReserva,
                reserva.horaEntrada,
                reserva.horaSaida
            )

            if (!disponivel) {
                return false
            }

            val novaReserva = reserva.copy(
                id = UUID.randomUUID().toString(),
                codigoReserva = "RES${(100000..999999).random()}"
            )
            reservaCache.add(novaReserva)
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun listaReservasPorUsuario(usuarioId: String): List<Reserva> {
        delay(1500)
        return reservaCache.filter { it.usuarioId == usuarioId }

    }

    override suspend fun listarReservasPorEstacionamento(estacionamentoId: String): List<Reserva> {
        delay(1000)
        return reservaCache.filter { it.estacionamentoId == estacionamentoId }
    }

    override suspend fun atualizarReserva(reserva: Reserva): Boolean {
        return try {
            delay(1200)
            val index = reservaCache.indexOfFirst { it.id == reserva.id }
            if (index != -1) {
                reservaCache[index] = reserva
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun cancelarReserva(reservaId: String): Boolean {
        return try {
            delay(1000)
            val reserva = reservaCache.find { it.id == reservaId }
            if (reserva != null && reserva.status in listOf("pendente", "confirmada")) {
                val index = reservaCache.indexOf(reserva)
                reservaCache[index] = reserva.copy(status = "cancelada")
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun confirmarReserva(reservaId: String): Boolean {
        return try {
            delay(800)
            val reserva = reservaCache.find { it.id == reservaId }
            if (reserva != null && reserva.status == "pendente") {
                val index = reservaCache.indexOf(reserva)
                reservaCache[index] = reserva.copy(status = "confirmada")
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getReservasAtivasPorUsuario(usuarioId: String): List<Reserva> {
        delay(700)
        return reservaCache.filter {
            it.usuarioId == usuarioId && it.status in listOf("confirmada", "ativa")
        }
    }


    override suspend fun getReservasFuturasPorUsuario(usuarioId: String): List<Reserva> {
        delay(700)
        val agora = Date()
        return reservaCache.filter {
            it.usuarioId == usuarioId &&
                    it.dataReserva.after(agora) &&
                    it.status != "cancelada"
        }
    }

    override suspend fun verificarDisponibilidade(
        estacionamentoId: String,
        data: Date,
        horaEntrada: String,
        horaSaida: String
    ): Boolean {
        delay(1000)
        return (0..9).random()< 8
    }
    override suspend fun calcularValorReserva(estacionamentoId: String, horas: Int): Double {
        delay(800)
        val estacionamento = estacionamentoRepository.getEstacionamentoPorId(estacionamentoId)
        return estacionamento?.valorHora?.times(horas) ?: 0.0
    }

    override suspend fun countReservasPorUsuario(usuarioId: String): Int {
        delay(300)
        return reservaCache.count { it.usuarioId == usuarioId }
    }

    override suspend fun getHistoricoReservas(usuarioId: String): List<Reserva> {
        delay(1200)
        return reservaCache.filter { it.usuarioId == usuarioId }
    }
}