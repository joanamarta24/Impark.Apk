package com.example.imparkapk.data.dao.local.dao.usuarios

import com.example.imparkapk.data.dao.model.Estacionamento
import com.example.imparkapk.data.dao.model.enus.TipoDeUsuario
import java.util.Date

data class DonoDao(
    val id: Long,
    val nome: String,
    val email: String,
    val senha: String,
    val telefone: String,
    val dataNascimento: Date,
    val tipoUsuario: TipoDeUsuario = TipoDeUsuario.CLIENTE,
    val estacionamentos: List<Estacionamento>?,
    val ativo: Boolean
)
