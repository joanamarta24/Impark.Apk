package com.example.imparkapk.data.mapper.usuarios

import com.example.imparkapk.data.local.entity.usuarios.DonoEntity
import com.example.imparkapk.data.remote.dto.usuarios.DonoDto
import com.example.imparkapk.domain.model.usuarios.Dono

fun Dono.toEntity() = DonoEntity(
    id = id,
    nome = nome,
    email = email,
    senha = senha,
    telefone = telefone,
    dataNascimento = dataNascimento,
    tipoUsuario = tipoUsuario,
    estacionamentosId = estacionamentos?.map { it.id } ?: emptyList(),
    ativo = ativo
)

fun DonoDto.toEntity() = DonoEntity(
    id = id,
    nome = nome,
    email = email,
    senha = senha,
    telefone = telefone,
    dataNascimento = dataNascimento,
    tipoUsuario = tipoUsuario,
    estacionamentosId = estacionamentosId,
    ativo = ativo
)

fun DonoEntity.toDomain() = Dono(
    id = id,
    nome = nome,
    email = email,
    senha = senha,
    telefone = telefone,
    dataNascimento = dataNascimento,
    tipoUsuario = tipoUsuario,
    estacionamentos = null,
    ativo = ativo
)

fun DonoDto.toDomain() = Dono(
    id = id,
    nome = nome,
    email = email,
    senha = senha,
    telefone = telefone,
    dataNascimento = dataNascimento,
    tipoUsuario = tipoUsuario,
    estacionamentos = null,
    ativo = ativo
)

fun Dono.toDto() = DonoDto(
    id = id,
    nome = nome,
    email = email,
    senha = senha,
    telefone = telefone,
    dataNascimento = dataNascimento,
    tipoUsuario = tipoUsuario,
    estacionamentosId = estacionamentos?.map { it.id } ?: emptyList(),
    ativo = ativo
)

fun DonoEntity.toDto() = DonoDto(
    id = id,
    nome = nome,
    email = email,
    senha = senha,
    telefone = telefone,
    dataNascimento = dataNascimento,
    tipoUsuario = tipoUsuario,
    estacionamentosId = estacionamentosId,
    ativo = ativo
)