package com.example.imparkapk.data.mapper.usuarios

import com.example.imparkapk.data.local.entity.usuarios.ClienteEntity
import com.example.imparkapk.data.remote.dto.usuarios.ClienteDto
import com.example.imparkapk.domain.model.Avaliacao
import com.example.imparkapk.domain.model.Carro
import com.example.imparkapk.domain.model.Reserva
import com.example.imparkapk.domain.model.usuarios.Cliente

fun Cliente.toDto() = ClienteDto(
    id = id,
    nome = nome,
    email = email,
    senha = senha,
    telefone = telefone,
    dataNascimento = dataNascimento,
    tipoUsuario = tipoUsuario,
    carros = carros?.map { it.id } ?: emptyList(),
    avaliacoes = avaliacoes?.map { it.id } ?: emptyList(),
    reservas = reservas?.map { it.id } ?: emptyList(),
    ativo = ativo
)

fun ClienteEntity.toDto() = ClienteDto(
    id = id,
    nome = nome,
    email = email,
    senha = senha,
    telefone = telefone,
    dataNascimento = dataNascimento,
    tipoUsuario = tipoUsuario,
    carros = carros,
    avaliacoes = avaliacoes,
    reservas = reservas,
    ativo = ativo
)

fun Cliente.toEntity() = ClienteEntity(
    id = id,
    nome = nome,
    email = email,
    senha = senha,
    telefone = telefone,
    dataNascimento = dataNascimento,
    tipoUsuario = tipoUsuario,
    carros = carros?.map { it.id } ?: emptyList(),
    avaliacoes = avaliacoes?.map { it.id } ?: emptyList(),
    reservas = reservas?.map { it.id } ?: emptyList(),
    ativo = ativo
)

fun ClienteDto.toEntity() = ClienteEntity(
    id = id,
    nome = nome,
    email = email,
    senha = senha,
    telefone = telefone,
    dataNascimento = dataNascimento,
    tipoUsuario = tipoUsuario,
    carros = carros,
    avaliacoes = avaliacoes,
    reservas = reservas,
    ativo = ativo
)

fun ClienteEntity.toDomain(
    carros: List<Carro>? = null,
    avaliacoes: List<Avaliacao>? = null,
    reservas: List<Reserva>? = null
) = Cliente(
    id = id,
    nome = nome,
    email = email,
    senha = senha,
    telefone = telefone,
    dataNascimento = dataNascimento,
    tipoUsuario = tipoUsuario,
    carros = carros,
    avaliacoes = avaliacoes,
    reservas = reservas,
    ativo = ativo
)

fun ClienteDto.toDomain(
    carros: List<Carro>? = null,
    avaliacoes: List<Avaliacao>? = null,
    reservas: List<Reserva>? = null
) = Cliente(
    id = id,
    nome = nome,
    email = email,
    senha = senha,
    telefone = telefone,
    dataNascimento = dataNascimento,
    tipoUsuario = tipoUsuario,
    carros = carros,
    avaliacoes = avaliacoes,
    reservas = reservas,
    ativo = ativo
)