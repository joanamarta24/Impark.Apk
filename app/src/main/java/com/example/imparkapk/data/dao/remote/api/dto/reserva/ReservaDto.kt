package com.example.imparkapk.data.dto.reserva

import com.example.imparkapk.data.dao.remote.api.dto.estacionamento.EstacionamentoDTO
import com.example.imparkapk.data.dto.carro.CarroDTO
import java.util.*

// ✅ REQUEST DTOs
data class ReservaCreateRequest(
    val clienteId: String,
    val estacionamentoId: String,
    val carroId: String,
    val dataHoraEntrada: String,
    val dataHoraSaida: String,
    val vagaNumero: Int,
    val placaVeiculo: String
)

data class ReservaUpdateRequest(
    val dataHoraEntrada: String? = null,
    val dataHoraSaida: String? = null,
    val vagaNumero: Int? = null,
    val status: String? = null,
    val placaVeiculo: String? = null
)

data class ReservaFilterRequest(
    val clienteId: String? = null,
    val estacionamentoId: String? = null,
    val status: String? = null,
    val dataInicio: String? = null,
    val dataFim: String? = null,
    val page: Int = 1,
    val pageSize: Int = 20
)

// ✅ RESPONSE DTOs
data class ReservaDTO(
    val id: String,
    val clienteId: String,
    val estacionamentoId: String,
    val dataHoraEntrada: String,
    val dataHoraSaida: String,
    val vagaNumero: Int,
    val status: String, // PENDENTE, CONFIRMADA, CANCELADA, FINALIZADA, ATIVA
    val valorTotal: Double,
    val placaVeiculo: String,
    val dataCriacao: Date,
    val dataAtualizacao: Date,
    val codigoReserva: String,
    val tempoRestante: String? = null,
    val valorPorHora: Double = 0.0
)

data class ReservaDetailResponse(
    val reserva: ReservaDTO,
    val estacionamento: EstacionamentoDTO,
    val carro: CarroDTO?,
    val cliente: com.example.imparkapk.data.dto.usuario.UsuarioDTO?
)

data class ReservaListResponse(
    val reservas: List<ReservaDTO>,
    val pagination: com.example.imparkapk.data.dto.shared.PaginationDTO
)

data class ReservaCreateResponse(
    val reserva: ReservaDTO,
    val qrCode: String? = null,
    val mensagem: String = "Reserva criada com sucesso"
)

data class ReservaStatusResponse(
    val reservaId: String,
    val status: String,
    val mensagem: String,
    val dataAtualizacao: Date
)

data class ReservaCheckinRequest(
    val reservaId: String,
    val codigoReserva: String
)

data class ReservaCheckoutRequest(
    val reservaId: String,
    val codigoReserva: String
)

data class ReservaValorResponse(
    val valorEstimado: Double,
    val tempoEstimado: String,
    val valorPorHora: Double,
    val taxas: List<TaxaDTO> = emptyList()
)

data class TaxaDTO(
    val nome: String,
    val valor: Double,
    val tipo: String // PERCENTUAL, FIXO
)