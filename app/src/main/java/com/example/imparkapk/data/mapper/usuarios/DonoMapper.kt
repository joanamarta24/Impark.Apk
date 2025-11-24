package com.example.imparkapk.data.mapper.usuarios

import com.example.imparkapk.data.local.entity.usuarios.DonoEntity
import com.example.imparkapk.data.remote.dto.usuarios.DonoDto
import com.example.imparkapk.domain.model.Estacionamento
import com.example.imparkapk.domain.model.usuarios.Dono

fun Dono.toEntity(
    pending: Boolean = false
) = DonoEntity(
    id = id,
    nome = nome,
    email = email,
    senha = senha,
    dataNascimento = dataNascimento,
    tipoUsuario = tipoUsuario,
    ativo = ativo,
    updatedAt = updatedAt,
    pendingSync = pending
)

fun DonoDto.toEntity(
    pending: Boolean = false
) = DonoEntity(
    id = id,
    nome = nome,
    email = email,
    senha = senha,
    dataNascimento = dataNascimento,
    tipoUsuario = tipoUsuario,
    ativo = ativo,
    updatedAt = updatedAt,
    pendingSync = pending
)

fun DonoEntity.toDomain(
    estacionamento: List<Estacionamento>? = emptyList()
) = Dono(
    id = id,
    nome = nome,
    email = email,
    senha = senha,
    dataNascimento = dataNascimento,
    tipoUsuario = tipoUsuario,
    estacionamentos = estacionamento,
    ativo = ativo,
    updatedAt = updatedAt
)

fun DonoDto.toDomain(
    estacionamento: List<Estacionamento>? = emptyList()
) = Dono(
    id = id,
    nome = nome,
    email = email,
    senha = senha,
    dataNascimento = dataNascimento,
    tipoUsuario = tipoUsuario,
    estacionamentos = estacionamento,
    ativo = ativo,
    updatedAt = updatedAt
)

fun Dono.toDto() = DonoDto(
    id = id,
    nome = nome,
    email = email,
    senha = senha,
    dataNascimento = dataNascimento,
    tipoUsuario = tipoUsuario,
    estacionamentosId = estacionamentos?.map { it.id } ?: emptyList(),
    ativo = ativo,
    updatedAt = updatedAt
)

fun DonoEntity.toDto() = DonoDto(
    id = id,
    nome = nome,
    email = email,
    senha = senha,
    dataNascimento = dataNascimento,
    tipoUsuario = tipoUsuario,
    ativo = ativo,
    updatedAt = updatedAt
)