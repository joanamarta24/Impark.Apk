package com.example.imparkapk.data.mapper

import com.example.imparkapk.data.local.entity.CarroEntity
import com.example.imparkapk.data.remote.dto.CarroDto
import com.example.imparkapk.domain.model.Carro
import com.example.imparkapk.domain.model.usuarios.Cliente

fun Carro.toEntity(
    pending: Boolean = false
) = CarroEntity(
    id = id,
    clienteId = cliente?.id,
    modelo = modelo,
    placa = placa,
    ativo = ativo,
    updatedAt = updatedAt,
    pendingSync = pending
)


fun CarroDto.toEntity(
    pending: Boolean = false
) = CarroEntity(
    id = id,
    clienteId = clienteId,
    modelo = modelo,
    placa = placa,
    ativo = ativo,
    updatedAt = updatedAt,
    pendingSync = pending,
)

fun CarroEntity.toDto() = CarroDto(
    id = id,
    clienteId = clienteId,
    modelo = modelo,
    placa = placa,
    ativo = ativo,
    updatedAt = updatedAt
)

fun Carro.toDto() = CarroDto(
    id = id,
    clienteId = cliente?.id,
    modelo = modelo,
    placa = placa,
    ativo = ativo,
    updatedAt = updatedAt
)

fun CarroEntity.toDomain(
    cliente: Cliente? = null
) = Carro(
    id = id,
    cliente = cliente,
    modelo = modelo,
    placa = placa,
    ativo = ativo,
    updatedAt = updatedAt
)

fun CarroDto.toDomain(
    cliente: Cliente? = null
) = Carro(
    id = id,
    cliente = cliente,
    modelo = modelo,
    placa = placa,
    ativo = ativo,
    updatedAt = updatedAt
)