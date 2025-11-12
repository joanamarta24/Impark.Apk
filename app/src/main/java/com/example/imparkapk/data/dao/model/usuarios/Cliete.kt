package com.example.imparkapk.data.dao.model.usuarios

import com.example.imparkapk.data.dao.model.Avaliacao
import com.example.imparkapk.data.dao.model.Reserva
import com.example.imparkapk.data.dao.model.enus.TipoDeUsuario
import com.example.imparktcc.model.Carro
import java.util.Date

data class Cliete(
    val id: Long,
    val nome: String,
    val email: String,
    val senha: String,
    val telefone: String,
    val dataNascimento: Date,
    val tipoUsuario: TipoDeUsuario = TipoDeUsuario.CLIENTE,
    val carros: List<Carro>?,
    val avaliacoes: List<Avaliacao>?,
    val reservas: List<Reserva>?,
    val ativo: Boolean
)
