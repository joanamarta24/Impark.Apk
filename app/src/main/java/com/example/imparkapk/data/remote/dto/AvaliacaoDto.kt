package com.example.imparkapk.data.remote.dto

import java.util.Date

data class AvaliacaoDto (
    val id: Long,
    val clienteId: Long?,
    val estacionamentoId: Long?,
    val nota: Int,
    val comentario: String,
    val dataAvaliacao: Date,
    val updatedAt: Long,
    val ativo: Boolean
)