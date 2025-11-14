package com.example.imparkapk.data.dao.dto

import java.util.Date

data class AvaliacaoDto(
    val id: Long,
    val usuarioId: String,
    val estacionamentoId: String,
    val nota: Int,
    val comentario: String?,
    val dataAvaliacao: Date,
    val usuarioNome: String? = null
)

