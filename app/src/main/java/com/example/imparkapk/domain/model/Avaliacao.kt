package com.example.imparkapk.domain.model

import com.example.imparkapk.domain.model.usuarios.Cliente
import java.util.Date

data class Avaliacao(
    val id: Long,
    val cliente: Cliente?,
    val estacionamento: Estacionamento?,
    val nota: Int,
    val comentario: String,
    val dataAvaliacao: Date,
    val ativo: Boolean
)
