package com.example.imparkapk.domain.model

import com.example.imparkapk.domain.model.usuarios.Cliente

data class Carro(
    val id: Long,
    val cliente: Cliente?,
    val modelo: String,
    val placa: String,
    val updatedAt: Long,
    val ativo: Boolean = true
)