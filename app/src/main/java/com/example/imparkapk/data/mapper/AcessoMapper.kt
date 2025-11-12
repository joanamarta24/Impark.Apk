package com.example.imparkapk.data.mapper

import com.example.imparkapk.data.local.entity.AcessoEntity
import com.example.imparkapk.data.remote.dto.AcessoDto
import com.example.imparkapk.domain.model.Acesso
import com.example.imparkapk.domain.model.Carro
import com.example.imparkapk.domain.model.Estacionamento

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

fun AcessoDto.toDomain(
    estacionamento: Estacionamento? = null,
    carro: Carro? = null
) = Acesso(
    id = id,
    estacionamento = estacionamento,
    carro = carro,
    placa = placa,
    horaDeEntrada = horaDeEntrada,
    horaDeSaida = horaDeSaida,
    horasTotais = horasTotais,
    valorTotal = valorTotal,
    ativo = ativo
)

fun AcessoEntity.toDomain(
    estacionamento: Estacionamento? = null,
    carro: Carro? = null
) = Acesso(
    id = id,
    estacionamento = estacionamento,
    carro = carro,
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