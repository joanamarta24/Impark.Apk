package com.example.imparkapk.data.dao.remote.api.dto

import java.time.LocalDateTime
import java.util.Date

data class AvaliacaoDto(
    val id: Long? = null,
    val estacionamentoId: Long,
    val usuarioId: String,
    val nota:Int,
    val comentario:String?,
    val dataAvalicao:Date,
    val usuarioNome:String? = null,
)
/// data/dto/avaliacao/AvaliacaoCreateDTO.kt

data class AvaliacaoCreateDto(
    val usuarioId: String,
    val estacionamentoId: Long,
    val nota: Int,
    val comentario: String? = null
)

