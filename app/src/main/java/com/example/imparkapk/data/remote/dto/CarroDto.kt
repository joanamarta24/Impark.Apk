package com.example.imparkapk.data.remote.dto

data class CarroDto (
    val id: Long,
    val usuarioId: Long?,
    val modelo: String,
    val placa: String,
    val ativo: Boolean = true
)