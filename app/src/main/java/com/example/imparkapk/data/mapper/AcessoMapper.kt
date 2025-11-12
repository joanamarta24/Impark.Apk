package com.example.imparkapk.data.mapper

import com.example.imparkapk.data.local.entity.AcessoEntity
import com.example.imparkapk.data.remote.dto.AcessoDto
import com.example.imparkapk.domain.model.Acesso

fun Acesso.toEntity() = AcessoEntity(
    id = id,
    estacionamentoId = estacionamento?.id,
    carroId = carro?.id,
    placa = placa,
    horaDeEntrada = horaDeEntrada,
    horaDeSaida = horaDeSaida,
    horasTotais = horasTotais,
    valorTotal = valorTotal,
    ativo = ativo
)

fun AcessoDto.toEntity() = AcessoEntity(
    id = id,
    estacionamentoId = estacionamentoId,
    carroId = carroId,
    placa = placa,
    horaDeEntrada = horaDeEntrada,
    horaDeSaida = horaDeSaida,
    horasTotais = horasTotais,
    valorTotal = valorTotal,
    ativo = ativo
)

fun AcessoDto.toDomain() = Acesso(
    id = id,
    estacionamento = null,
    carro = null,
    placa = placa,
    horaDeEntrada = horaDeEntrada,
    horaDeSaida = horaDeSaida,
    horasTotais = horasTotais,
    valorTotal = valorTotal,
    ativo = ativo
)

fun AcessoEntity.toDomain() = Acesso(
    id = id,
    estacionamento = null,
    carro = null,
    placa = placa,
    horaDeEntrada = horaDeEntrada,
    horaDeSaida = horaDeSaida,
    horasTotais = horasTotais,
    valorTotal = valorTotal,
    ativo = ativo
)

fun Acesso.toDto() = AcessoDto(
    id = id,
    estacionamentoId = estacionamento?.id,
    carroId = carro?.id,
    placa = placa,
    horaDeEntrada = horaDeEntrada,
    horaDeSaida = horaDeSaida,
    horasTotais = horasTotais,
    valorTotal = valorTotal,
    ativo = ativo
)

fun AcessoEntity.toDto() = AcessoDto(
    id = id,
    estacionamentoId = estacionamentoId,
    carroId = carroId,
    placa = placa,
    horaDeEntrada = horaDeEntrada,
    horaDeSaida = horaDeSaida,
    horasTotais = horasTotais,
    valorTotal = valorTotal,
    ativo = ativo
)