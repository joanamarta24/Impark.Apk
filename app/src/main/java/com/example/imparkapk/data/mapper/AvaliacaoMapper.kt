package com.example.imparkapk.data.mapper

import com.example.imparkapk.data.local.entity.AvaliacaoEntity
import com.example.imparkapk.data.remote.dto.AvaliacaoDto
import com.example.imparkapk.domain.model.Avaliacao
import com.example.imparkapk.domain.model.Estacionamento
import com.example.imparkapk.domain.model.usuarios.Cliente

fun Avaliacao.toDto() = AvaliacaoDto(
    id = id,
    clienteId = cliente?.id,
    estacionamentoId = estacionamento?.id,
    nota = nota,
    comentario = comentario,
    dataAvaliacao = dataAvaliacao,
    ativo = ativo,
    updatedAt = updatedAt
)

fun AvaliacaoEntity.toDto() = AvaliacaoDto(
    id = id,
    clienteId = clienteId,
    estacionamentoId = estacionamentoId,
    nota = nota,
    comentario = comentario,
    dataAvaliacao = dataAvaliacao,
    ativo = ativo,
    updatedAt = updatedAt
)

fun AvaliacaoDto.toEntity(
    pending: Boolean = false
) = AvaliacaoEntity(
    id = id,
    clienteId = clienteId,
    estacionamentoId = estacionamentoId,
    nota = nota,
    comentario = comentario,
    dataAvaliacao = dataAvaliacao,
    ativo = ativo,
    updatedAt = updatedAt,
    pendingSync = pending,
)

fun Avaliacao.toEntity(
    pending: Boolean = false
) = AvaliacaoEntity(
    id = id,
    clienteId = cliente?.id,
    estacionamentoId = estacionamento?.id,
    nota = nota,
    comentario = comentario,
    dataAvaliacao = dataAvaliacao,
    ativo = ativo,
    updatedAt = updatedAt,
    pendingSync = pending,
)

fun AvaliacaoEntity.toDomain(
    cliente: Cliente?,
    estacionamento: Estacionamento?
) = Avaliacao(
    id = id,
    cliente = cliente,
    estacionamento = estacionamento,
    nota = nota,
    comentario = comentario,
    dataAvaliacao = dataAvaliacao,
    ativo = ativo,
    updatedAt = updatedAt
)

fun AvaliacaoDto.toDomain(
    cliente: Cliente?,
    estacionamento: Estacionamento?
) = Avaliacao(
    id = id,
    cliente = cliente,
    estacionamento = estacionamento,
    nota = nota,
    comentario = comentario,
    dataAvaliacao = dataAvaliacao,
    ativo = ativo,
    updatedAt = updatedAt
)