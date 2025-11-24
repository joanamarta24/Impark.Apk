package com.example.imparkapk.data.mapper.usuarios

import com.example.imparkapk.data.local.entity.usuarios.GerenteEntity
import com.example.imparkapk.data.remote.dto.usuarios.GerenteDto
import com.example.imparkapk.domain.model.Estacionamento
import com.example.imparkapk.domain.model.usuarios.Gerente


fun GerenteEntity.toDto() = GerenteDto(
    id = id,
    nome = nome,
    email = email,
    senha = senha,
    dataNascimento = dataNascimento,
    tipoUsuario = tipoUsuario,
    estacionamentoId = estacionamentoId,
    ativo = ativo,
    updatedAt = updatedAt
)

fun GerenteDto.toDto() = GerenteDto(
    id = id,
    nome = nome,
    email = email,
    senha = senha,
    dataNascimento = dataNascimento,
    tipoUsuario = tipoUsuario,
    estacionamentoId = estacionamentoId,
    ativo = ativo,
    updatedAt = updatedAt
)
fun GerenteEntity.toDomain(
    estacionamento: Estacionamento?
) = Gerente(
    id = id,
    nome = nome,
    email = email,
    senha = senha,
    dataNascimento = dataNascimento,
    tipoUsuario = tipoUsuario,
    estacionamento = estacionamento,
    ativo = ativo,
    updatedAt = updatedAt
)

fun GerenteDto.toDomain(
    estacionamento: Estacionamento?
) = Gerente(
    id = id,
    nome = nome,
    email = email,
    senha = senha,
    dataNascimento = dataNascimento,
    tipoUsuario = tipoUsuario,
    estacionamento = estacionamento,
    ativo = ativo,
    updatedAt = updatedAt
)

fun Gerente.toEntity(
    pending: Boolean = false
) = GerenteEntity(
    id = id,
    nome = nome,
    email = email,
    senha = senha,
    dataNascimento = dataNascimento,
    tipoUsuario = tipoUsuario,
    estacionamentoId = estacionamento?.id ?: 0,
    ativo = ativo,
    updatedAt = updatedAt,
    pendingSync = pending,
)

fun GerenteDto.toEntity(
    pending: Boolean = false
) = GerenteEntity(
    id = id,
    nome = nome,
    email = email,
    senha = senha,
    dataNascimento = dataNascimento,
    tipoUsuario = tipoUsuario,
    estacionamentoId = estacionamentoId,
    ativo = ativo,
    updatedAt = updatedAt,
    pendingSync = pending
)