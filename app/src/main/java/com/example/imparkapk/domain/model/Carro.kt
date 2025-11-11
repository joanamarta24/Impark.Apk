package com.example.imparkapk.domain.model

data class Carro(
    val id: Long,
    val usuarioId: String,
    val modelo: String,
    val placa: String,
    val ativo: Boolean = true
)