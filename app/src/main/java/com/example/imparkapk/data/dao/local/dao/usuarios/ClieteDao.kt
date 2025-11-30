package com.example.imparkapk.data.dao.local.dao.usuarios

import com.example.imparkapk.data.dao.model.Avaliacao
import com.example.imparkapk.data.dao.model.Reserva
import com.example.imparkapk.data.dao.model.enus.TipoUsuario
import com.example.imparkapk.domain.model.Carro
import java.util.Date

data class ClieteDao(
    val id: Long,
    val nome: String,
    val email: String,
    val senha: String,
    val telefone: String,
    val dataNascimento: Date,
    val tipoUsuario: TipoUsuario = TipoUsuario.CLIENTE,
    val carros: List<Carro>?,
    val avaliacoes: List<Avaliacao>?,
    val reservas: List<Reserva>?,
    val ativo: Boolean
)
