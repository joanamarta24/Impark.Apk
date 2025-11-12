package com.example.imparkapk.data.mapper

import com.example.imparkapk.data.local.entity.CarroEntity
import com.example.imparkapk.data.remote.dto.CarroDto
import com.example.imparkapk.domain.model.Carro
import com.example.imparkapk.domain.model.usuarios.Cliente

fun Carro.toEntity() = CarroEntity(
    id = id,
    usuarioId = cliente?.id,
    modelo = modelo,
    placa = placa,
    ativo = ativo
)


fun CarroDto.toEntity() = CarroEntity(
    id = id,
    usuarioId = usuarioId,
    modelo = modelo,
    placa = placa,
    ativo = ativo
)

fun CarroEntity.toDto() = CarroDto(
    id = id,
    usuarioId = usuarioId,
    modelo = modelo,
    placa = placa,
    ativo = ativo
)

fun Carro.toDto() = CarroDto(
    id = id,
    usuarioId = cliente?.id,
    modelo = modelo,
    placa = placa,
    ativo = ativo
)

fun CarroEntity.toDomain(
    cliente: Cliente? = null
) = Carro(
    id = id,
    cliente = cliente,
    modelo = modelo,
    placa = placa,
    ativo = ativo
)

fun CarroDto.toDomain(
    cliente: Cliente? = null
) = Carro(
    id = id,
    cliente = cliente,
    modelo = modelo,
    placa = placa,
    ativo = ativo
)