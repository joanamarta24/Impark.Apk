package com.example.imparkapk.data.dao.remote.api.dto

import java.time.LocalDateTime

data class CarroDto(
    val id:Long? = null,
    val placa:String,
    val modelo:String,
    val marca:String,
    val cor:String,
    val ano:String,
    val usuarioId:String? = null,
    val usuarioNome:String? = null,
    val isAtivo:Boolean = true,
    val observacoes: String? = null,
    val tipoVeiculo: String? = "CARRO", // CARRO, MOTO, etc.
    val fotoUrl: String? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
)
