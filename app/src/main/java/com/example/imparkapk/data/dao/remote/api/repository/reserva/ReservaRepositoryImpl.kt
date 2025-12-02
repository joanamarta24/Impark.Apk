package com.example.imparkapk.data.dao.remote.api.repository.reserva

import com.example.imparkapk.data.dao.local.dao.dao.ReservaDao
import com.example.imparkapk.data.dao.model.Reserva
import com.example.imparkapk.data.dao.remote.api.api.ReservaApi
import com.example.imparkapk.data.dao.remote.api.repository.estacionamento.EstacionamentoRepository
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReservaRepositoryImpl @Inject constructor(
    private val reservaDao: ReservaDao,
    private val reservaApi: ReservaApi,
    private val estacionamentoRepository: EstacionamentoRepository
) : ReservaRepository {
    private val reservaCache = mutableListOf<Reserva>()

    init {
        // Dados de demonstração
        val calendario = Calendar.getInstance()

        // Reserva futura
        calendario.time = Date()
        calendario.add(Calendar.DAY_OF_MONTH, 1)
        val reservaFutura = calendario.time

        // Reserva passada
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
                    dataReserva = reservaFutura,
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

    override suspend fun criarReserva(reserva: Reserva): Result<Reserva> {
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
                return Result.failure(Exception("Horário não disponível"))
            }

            val novaReserva = reserva.copy(
                id = UUID.randomUUID().toString(),
                codigoReserva = "RES${(100000..999999).random()}",
                dataCriacao = Date()
            )

            reservaCache.add(novaReserva)

            // Salvar no banco local
            reservaDao.inserirReserva(novaReserva.toEntity())

            Result.success(novaReserva)
        } catch (e: Exception) {
            Result.failure(Exception("Erro ao criar reserva: ${e.message}"))
        }
    }

    override suspend fun getReservaPorId(id: String): Result<Reserva?> {
        return try {
            // Primeiro tenta da API
            val response = reservaApi.getReserva(id)
            if (response.isSuccessful && response.body() != null) {
                val reservaApi = response.body()!!
                val reserva = mapApiResponseToReserva(reservaApi)

                // Atualiza cache
                val index = reservaCache.indexOfFirst { it.id == id }
                if (index != -1) {
                    reservaCache[index] = reserva
                } else {
                    reservaCache.add(reserva)
                }

                Result.success(reserva)
            } else {
                // Fallback: busca do cache
                val reservaCacheEncontrada = reservaCache.find { it.id == id }
                if (reservaCacheEncontrada != null) {
                    Result.success(reservaCacheEncontrada)
                } else {
                    Result.success(null)
                }
            }
        } catch (e: Exception) {
            // Fallback final: busca do banco local
            val entity = reservaDao.getReservaById(id)
            if (entity != null) {
                val reserva = mapEntityToReserva(entity)
                Result.success(reserva)
            } else {
                Result.failure(Exception("Reserva não encontrada"))
            }
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

    override suspend fun obterTodasReservas(): Result<List<Reserva>> {
        return try {
            // Tenta da API primeiro
            val response = reservaApi.getAllReservas()
            if (response.isSuccessful && response.body() != null) {
                val reservasApi = response.body()!!
                val reservas = reservasApi.map { mapApiResponseToReserva(it) }

                // Atualiza cache
                reservaCache.clear()
                reservaCache.addAll(reservas)

                Result.success(reservas)
            } else {
                // Fallback: retorna do cache
                Result.success(reservaCache)
            }
        } catch (e: Exception) {
            // Fallback: banco local
            val entities = reservaDao.getAllReservas()
            val reservas = entities.map { mapEntityToReserva(it) }
            Result.success(reservas)
        }
    }

    override suspend fun atualizarReserva(reserva: Reserva): Result<Boolean> {
        return try {
            delay(1200)
            val index = reservaCache.indexOfFirst { it.id == reserva.id }
            if (index != -1) {
                reservaCache[index] = reserva.copy(dataAtualizacao = Date())

                // Atualizar na API
                reservaApi.atualizarReserva(reserva.id, reserva.toRequest())

                // Atualizar no banco local
                reservaDao.atualizarReserva(reserva.toEntity())

                Result.success(true)
            } else {
                Result.success(false)
            }
        } catch (e: Exception) {
            Result.failure(Exception("Erro ao atualizar reserva: ${e.message}"))
        }
    }

    override suspend fun cancelarReserva(reservaId: String): Result<Boolean> {
        return try {
            delay(1000)
            val reserva = reservaCache.find { it.id == reservaId }
            if (reserva != null && reserva.status in listOf("pendente", "confirmada")) {
                val index = reservaCache.indexOf(reserva)
                reservaCache[index] = reserva.copy(
                    status = "cancelada",
                    dataAtualizacao = Date()
                )

                // Cancelar na API
                reservaApi.cancelarReserva(reservaId)

                Result.success(true)
            } else {
                Result.success(false)
            }
        } catch (e: Exception) {
            Result.failure(Exception("Erro ao cancelar reserva: ${e.message}"))
        }
    }

    override suspend fun confirmarReserva(reservaId: String): Result<Boolean> {
        return try {
            delay(800)
            val reserva = reservaCache.find { it.id == reservaId }
            if (reserva != null && reserva.status == "pendente") {
                val index = reservaCache.indexOf(reserva)
                reservaCache[index] = reserva.copy(
                    status = "confirmada",
                    dataAtualizacao = Date()
                )

                Result.success(true)
            } else {
                Result.success(false)
            }
        } catch (e: Exception) {
            Result.failure(Exception("Erro ao confirmar reserva: ${e.message}"))
        }
    }

    override suspend fun getReservasPorData(
        data: Date,
        estacionamentoId: String
    ): Result<List<Reserva>> {
        return try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val dataFormatada = dateFormat.format(data)

            val response = reservaApi.getReservasPorData(estacionamentoId, dataFormatada)
            if (response.isSuccessful && response.body() != null) {
                val reservasApi = response.body()!!
                val reservas = reservasApi.map { mapApiResponseToReserva(it) }
                Result.success(reservas)
            } else {
                // Fallback: busca do cache
                val reservasFiltradas = reservaCache.filter {
                    dateFormat.format(it.dataReserva) == dataFormatada &&
                            it.estacionamentoId == estacionamentoId
                }
                Result.success(reservasFiltradas)
            }
        } catch (e: Exception) {
            Result.failure(Exception("Erro ao buscar reservas por data: ${e.message}"))
        }
    }

    override suspend fun getReservasAtivasPorUsuario(usuarioId: String): Result<List<Reserva>> {
        return try {
            delay(700)
            val reservasAtivas = reservaCache.filter {
                it.usuarioId == usuarioId &&
                        it.status in listOf("confirmada", "ativa", "pendente")
            }
            Result.success(reservasAtivas)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getReservasFuturasPorUsuario(usuarioId: String): Result<List<Reserva>> {
        return try {
            delay(700)
            val agora = Date()
            val reservasFuturas = reservaCache.filter {
                it.usuarioId == usuarioId &&
                        it.dataReserva.after(agora) &&
                        it.status != "cancelada"
            }
            Result.success(reservasFuturas)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun verificarDisponibilidade(
        estacionamentoId: String,
        data: Date,
        horaEntrada: String,
        horaSaida: String
    ): Result<Boolean> {
        return try {
            delay(1000)

            // Verifica no cache primeiro
            val reservasNoDia = reservaCache.filter {
                val sameDay = isSameDay(it.dataReserva, data)
                sameDay && it.estacionamentoId == estacionamentoId &&
                        it.status in listOf("confirmada", "pendente")
            }

            // Verifica conflito de horário
            val temConflito = reservasNoDia.any { reserva ->
                temConflitoHorario(
                    horaEntrada = horaEntrada,
                    horaSaida = horaSaida,
                    reservaHoraEntrada = reserva.horaEntrada,
                    reservaHoraSaida = reserva.horaSaida
                )
            }

            Result.success(!temConflito)
        } catch (e: Exception) {
            Result.failure(Exception("Erro ao verificar disponibilidade: ${e.message}"))
        }
    }

    override suspend fun calcularValorReserva(
        estacionamentoId: String,
        horas: Int
    ): Result<Double> {
        return try {
            delay(800)
            val estacionamento = estacionamentoRepository.buscarEstacionamentoPorId(estacionamentoId)
            val valorHora = estacionamento?.valorHora ?: 0.0
            val valorTotal = valorHora * horas

            Result.success(valorTotal)
        } catch (e: Exception) {
            Result.failure(Exception("Erro ao calcular valor: ${e.message}"))
        }
    }

    override suspend fun sincronizarReservas(): Result<Boolean> {
        return try {
            // Busca todas as reservas da API
            val response = reservaApi.getAllReservas()
            if (response.isSuccessful && response.body() != null) {
                val reservasApi = response.body()!!
                val reservas = reservasApi.map { mapApiResponseToReserva(it) }

                // Atualiza cache
                reservaCache.clear()
                reservaCache.addAll(reservas)

                // Atualiza banco local
                reservaDao.inserirReservasEmLote(reservas.map { it.toEntity() })

                Result.success(true)
            } else {
                Result.success(false)
            }
        } catch (e: Exception) {
            Result.failure(Exception("Erro na sincronização: ${e.message}"))
        }
    }

    override suspend fun countReservasPorUsuario(usuarioId: String): Result<Int> {
        return try {
            delay(300)
            val count = reservaCache.count { it.usuarioId == usuarioId }
            Result.success(count)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getHistoricoReservas(usuarioId: String): Result<List<Reserva>> {
        return try {
            delay(1200)
            val agora = Date()
            val historico = reservaCache.filter {
                it.usuarioId == usuarioId &&
                        (it.dataReserva.before(agora) || it.status in listOf("cancelada", "concluida"))
            }.sortedByDescending { it.dataReserva }

            Result.success(historico)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun obterReservasPorUsuario(usuarioId: String): Result<List<Reserva>> {
        return try {
            // Método que o ViewModel está tentando usar
            val response = reservaApi.getReservasByUsuario(usuarioId)
            if (response.isSuccessful && response.body() != null) {
                val reservasApi = response.body()!!
                val reservas = reservasApi.map { mapApiResponseToReserva(it) }
                Result.success(reservas)
            } else {
                // Fallback: usa o método existente
                val reservas = listaReservasPorUsuario(usuarioId)
                Result.success(reservas)
            }
        } catch (e: Exception) {
            Result.failure(Exception("Erro ao obter reservas do usuário: ${e.message}"))
        }
    }

    // Métodos auxiliares privados
    private fun mapApiResponseToReserva(apiResponse: ReservaApi): Reserva {
        return Reserva(
            id = apiResponse.id ?: "",
            usuarioId = apiResponse.usuarioId ?: "",
            carroId = apiResponse.carroId ?: "",
            estacionamentoId = apiResponse.estacionamentoId ?: "",
            dataReserva = apiResponse.dataReserva ?: Date(),
            horaEntrada = apiResponse.horaEntrada ?: "",
            horaSaida = apiResponse.horaSaida ?: "",
            valorTotal = apiResponse.valorTotal ?: 0.0,
            status = apiResponse.status ?: "pendente",
            codigoReserva = apiResponse.codigoReserva ?: "",
            dataCriacao = apiResponse.dataCriacao ?: Date(),
            dataAtualizacao = apiResponse.dataAtualizacao
        )
    }

    private fun mapEntityToReserva(entity: ReservaEntity): Reserva {
        return Reserva(
            id = entity.id,
            usuarioId = entity.usuarioId,
            carroId = entity.carroId,
            estacionamentoId = entity.estacionamentoId,
            dataReserva = entity.dataReserva,
            horaEntrada = entity.horaEntrada,
            horaSaida = entity.horaSaida,
            valorTotal = entity.valorTotal,
            status = entity.status,
            codigoReserva = entity.codigoReserva,
            dataCriacao = entity.dataCriacao,
            dataAtualizacao = entity.dataAtualizacao
        )
    }

    private fun isSameDay(date1: Date, date2: Date): Boolean {
        val cal1 = Calendar.getInstance().apply { time = date1 }
        val cal2 = Calendar.getInstance().apply { time = date2 }
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)
    }

    private fun temConflitoHorario(
        horaEntrada: String,
        horaSaida: String,
        reservaHoraEntrada: String,
        reservaHoraSaida: String
    ): Boolean {
        val entrada = parseHoraParaMinutos(horaEntrada)
        val saida = parseHoraParaMinutos(horaSaida)
        val reservaEntrada = parseHoraParaMinutos(reservaHoraEntrada)
        val reservaSaida = parseHoraParaMinutos(reservaHoraSaida)

        return (entrada < reservaSaida && saida > reservaEntrada)
    }

    private fun parseHoraParaMinutos(hora: String): Int {
        val partes = hora.split(":")
        if (partes.size == 2) {
            val horas = partes[0].toIntOrNull() ?: 0
            val minutos = partes[1].toIntOrNull() ?: 0
            return horas * 60 + minutos
        }
        return 0
    }
}