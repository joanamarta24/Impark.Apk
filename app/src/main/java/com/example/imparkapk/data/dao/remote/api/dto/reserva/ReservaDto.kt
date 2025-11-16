package com.example.imparkapk.data.dao.remote.api.dto.reserva

import java.time.LocalDateTime

data class ReservaDto(
    val clienteId: Long,
    val estacionamentoId: Long,
    val carroId: Long,
    val dataHoraInicio: LocalDateTime,
    val dataHoraFim: LocalDateTime,
    val vagaNumero: Int,
    val placaVeiculo: String
)

data class ReservaUpdateRequest(
    val dataHoraInicio: LocalDateTime? = null,
    val dataHoraFim: LocalDateTime? = null,
    val vagaNumero: Int? = null,
    val placaVeiculo: String? = null
)

data class ReservaUpdateResponse(
    val clienteId: Long? = null,
    val estacionamentoId: Long? = null,
    val status: String? = null,
    val dataInicio: String? = null,
    val dataFim: String? = null,
    val page: Int = 1,
    val pageSize: Int = 20
)
//Response
data class ReservaDto(
    val id: Long,
    val clienteId:Long,
    val estacionamentoId: Long,
    val dataHoraEntrada: LocalDateTime,
    val dataHoraSaida: LocalDateTime,
    val vagaNumero: Int,
    val
)
