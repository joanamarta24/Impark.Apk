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
    telefone = telefone,
    dataNascimento = dataNascimento,
    tipoUsuario = tipoUsuario,
    estacionamentoId = estacionamentoId,
    ativo = ativo
)

fun GerenteDto.toDto() = GerenteDto(
    id = id,
    nome = nome,
    email = email,
    senha = senha,
    telefone = telefone,
    dataNascimento = dataNascimento,
    tipoUsuario = tipoUsuario,
    estacionamentoId = estacionamentoId,
    ativo = ativo
)
fun GerenteEntity.toDomain(
    estacionamento: Estacionamento?
) = Gerente(
    id = id,
    nome = nome,
    email = email,
    senha = senha,
    telefone = telefone,
    dataNascimento = dataNascimento,
    tipoUsuario = tipoUsuario,
    estacionamento = estacionamento,
    ativo = ativo
)

fun GerenteDto.toDomain(
    estacionamento: Estacionamento?
) = Gerente(
    id = id,
    nome = nome,
    email = email,
    senha = senha,
    telefone = telefone,
    dataNascimento = dataNascimento,
    tipoUsuario = tipoUsuario,
    estacionamento = estacionamento,
    ativo = ativo
)

fun Gerente.toEntity() = GerenteEntity(
    id = id,
    nome = nome,
    email = email,
    senha = senha,
    telefone = telefone,
    dataNascimento = dataNascimento,
    tipoUsuario = tipoUsuario,
    estacionamentoId = estacionamento?.id ?: 0,
    ativo = ativo
)

fun GerenteDto.toEntity() = GerenteEntity(
    id = id,
    nome = nome,
    email = email,
    senha = senha,
    telefone = telefone,
    dataNascimento = dataNascimento,
    tipoUsuario = tipoUsuario,
    estacionamentoId = estacionamentoId,
    ativo = ativo
)