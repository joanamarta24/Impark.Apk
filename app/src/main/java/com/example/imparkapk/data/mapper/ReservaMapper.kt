package com.example.imparkapk.data.mapper

import com.example.imparkapk.data.local.entity.ReservaEntity
import com.example.imparkapk.data.remote.dto.ReservaDto
import com.example.imparkapk.domain.model.Carro
import com.example.imparkapk.domain.model.Estacionamento
import com.example.imparkapk.domain.model.Reserva
import com.example.imparkapk.domain.model.usuarios.Cliente

fun Reserva.toDto() = ReservaDto(
    id = id,
    usuarioId = cliente?.id,
    carroId = carro?.id,
    estacionamentoId = estacionamento?.id,
    dataReserva = dataReserva,
    horaEntrada = horaEntrada,
    horaSaida = horaSaida,
    valorTotal = valorTotal,
    status = status,
    ativo = ativo,
    updatedAt = updatedAt
)

fun ReservaEntity.toDto() = ReservaDto(
    id = id,
    usuarioId = usuarioId,
    carroId = carroId,
    estacionamentoId = estacionamentoId,
    dataReserva = dataReserva,
    horaEntrada = horaEntrada,
    horaSaida = horaSaida,
    valorTotal = valorTotal,
    status = status,
    ativo = ativo,
    updatedAt = updatedAt
)

fun Reserva.toEntity(
    pending: Boolean = false
) = ReservaEntity(
    id = id,
    usuarioId = cliente?.id,
    carroId = carro?.id,
    estacionamentoId = estacionamento?.id,
    dataReserva = dataReserva,
    horaEntrada = horaEntrada,
    horaSaida = horaSaida,
    valorTotal = valorTotal,
    status = status,
    ativo = ativo,
    updatedAt = updatedAt,
    pendingSync = pending,
)

fun ReservaDto.toEntity(
    pending: Boolean = false
) = ReservaEntity(
    id = id,
    usuarioId = usuarioId,
    carroId = carroId,
    estacionamentoId = estacionamentoId,
    dataReserva = dataReserva,
    horaEntrada = horaEntrada,
    horaSaida = horaSaida,
    valorTotal = valorTotal,
    status = status,
    ativo = ativo,
    updatedAt = updatedAt,
    pendingSync = pending
)

fun ReservaDto.toDomain(
    usuario: Cliente? = null,
    carro: Carro? = null,
    estacionamento: Estacionamento? = null
) = Reserva(
    id = id,
    cliente = usuario,
    carro = carro,
    estacionamento = estacionamento,
    dataReserva = dataReserva,
    horaEntrada = horaEntrada,
    horaSaida = horaSaida,
    valorTotal = valorTotal,
    status = status,
    ativo = ativo,
    updatedAt = updatedAt
)

fun ReservaEntity.toDomain(
    usuario: Cliente? = null,
    carro: Carro? = null,
    estacionamento: Estacionamento? = null
) = Reserva(
    id = id,
    cliente = usuario,
    carro = carro,
    estacionamento = estacionamento,
    dataReserva = dataReserva,
    horaEntrada = horaEntrada,
    horaSaida = horaSaida,
    valorTotal = valorTotal,
    status = status,
    ativo = ativo,
    updatedAt = updatedAt
)