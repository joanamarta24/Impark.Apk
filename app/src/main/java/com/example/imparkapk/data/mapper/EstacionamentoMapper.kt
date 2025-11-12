package com.example.imparkapk.data.mapper

import com.example.imparkapk.data.local.entity.EstacionamentoEntity
import com.example.imparkapk.data.remote.dto.EstacionamentoDto
import com.example.imparkapk.domain.model.Estacionamento
import com.example.imparkapk.domain.model.Reserva
import com.example.imparkapk.domain.model.usuarios.Dono
import com.example.imparkapk.domain.model.usuarios.Gerente

fun Estacionamento.toDto() = EstacionamentoDto(
    id = id,
    nome = nome,
    endereco = endereco,
    latitude = latitude,
    longitude = longitude,
    totalVagas = totalVagas,
    vagasDisponiveis = vagasDisponiveis,
    valorHora = valorHora,
    telefone = telefone,
    horarioAbertura = horarioAbertura,
    horarioFechamento = horarioFechamento,
    ativo = ativo,
    vagasTotal = vagasTotal,
    precoHora = precoHora,
    horarioFuncionamento = horarioFuncionamento,
    reservasId = reservas?.map { it.id } ?: emptyList(),
    donoId = dono?.id,
    gerentesId = gerentes?.map { it.id } ?: emptyList()
)

fun EstacionamentoEntity.toDto() = EstacionamentoDto(
    id = id,
    nome = nome,
    endereco = endereco,
    latitude = latitude,
    longitude = longitude,
    totalVagas = totalVagas,
    vagasDisponiveis = vagasDisponiveis,
    valorHora = valorHora,
    telefone = telefone,
    horarioAbertura = horarioAbertura,
    horarioFechamento = horarioFechamento,
    ativo = ativo,
    vagasTotal = vagasTotal,
    precoHora = precoHora,
    horarioFuncionamento = horarioFuncionamento,
    reservasId = reservasId,
    donoId = donoId,
    gerentesId = gerentesId
)


fun Estacionamento.toEntity() = EstacionamentoEntity(
    id = id,
    nome = nome,
    endereco = endereco,
    latitude = latitude,
    longitude = longitude,
    totalVagas = totalVagas,
    vagasDisponiveis = vagasDisponiveis,
    valorHora = valorHora,
    telefone = telefone,
    horarioAbertura = horarioAbertura,
    horarioFechamento = horarioFechamento,
    ativo = ativo,
    vagasTotal = vagasTotal,
    precoHora = precoHora,
    horarioFuncionamento = horarioFuncionamento,
    reservasId = reservas?.map { it.id } ?: emptyList(),
    donoId = dono?.id,
    gerentesId = gerentes?.map { it.id } ?: emptyList()
)

fun EstacionamentoDto.toEntity() = EstacionamentoEntity(
    id = id,
    nome = nome,
    endereco = endereco,
    latitude = latitude,
    longitude = longitude,
    totalVagas = totalVagas,
    vagasDisponiveis = vagasDisponiveis,
    valorHora = valorHora,
    telefone = telefone,
    horarioAbertura = horarioAbertura,
    horarioFechamento = horarioFechamento,
    ativo = ativo,
    vagasTotal = vagasTotal,
    precoHora = precoHora,
    horarioFuncionamento = horarioFuncionamento,
    reservasId = reservasId,
    donoId = donoId,
    gerentesId = gerentesId
)

fun EstacionamentoDto.toDomain(
    reservas: List<Reserva>? = null,
    dono: Dono? = null,
    gerentes: List<Gerente>? = null
) = Estacionamento(
    id = id,
    nome = nome,
    endereco = endereco,
    latitude = latitude,
    longitude = longitude,
    totalVagas = totalVagas,
    vagasDisponiveis = vagasDisponiveis,
    valorHora = valorHora,
    telefone = telefone,
    horarioAbertura = horarioAbertura,
    horarioFechamento = horarioFechamento,
    ativo = ativo,
    vagasTotal = vagasTotal,
    precoHora = precoHora,
    horarioFuncionamento = horarioFuncionamento,
    reservas = reservas,
    dono = dono,
    gerentes = gerentes
)

fun EstacionamentoEntity.toDomain(
    reservas: List<Reserva>? = null,
    dono: Dono? = null,
    gerentes: List<Gerente>? = null
) = Estacionamento(
    id = id,
    nome = nome,
    endereco = endereco,
    latitude = latitude,
    longitude = longitude,
    totalVagas = totalVagas,
    vagasDisponiveis = vagasDisponiveis,
    valorHora = valorHora,
    telefone = telefone,
    horarioAbertura = horarioAbertura,
    horarioFechamento = horarioFechamento,
    ativo = ativo,
    vagasTotal = vagasTotal,
    precoHora = precoHora,
    horarioFuncionamento = horarioFuncionamento,
    reservas = reservas,
    dono = dono,
    gerentes = gerentes
)