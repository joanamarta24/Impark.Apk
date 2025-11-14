package com.example.imparkapk.domain.model.usuarios

import com.example.imparkapk.domain.model.Avaliacao
import com.example.imparkapk.domain.model.Carro
import com.example.imparkapk.domain.model.Reserva
import com.example.imparkapk.domain.model.enuns.TipoDeUsuario
import java.util.Date

data class Cliente (

    val id: Long,
    val nome: String,
    val email: String,
    val senha: String?,
    val telefone: String,
    val dataNascimento: Date,
    val tipoUsuario: TipoDeUsuario = TipoDeUsuario.CLIENTE,
    val carros: List<Carro>?,
    val avaliacoes: List<Avaliacao>?,
    val reservas: List<Reserva>?,
    val updatedAt: Long,
    val ativo: Boolean
)