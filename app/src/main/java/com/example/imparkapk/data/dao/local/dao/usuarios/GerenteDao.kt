package com.example.imparkapk.data.dao.local.dao.usuarios

import com.example.imparkapk.data.dao.model.Estacionamento
import com.example.imparkapk.data.dao.model.enus.TipoUsuario
import java.util.Date

data class GerenteDao(
    val id: Long,
    val nome: String,
    val email: String,
    val senha: String,
    val telefone: String,
    val dataNascimento: Date,
    val tipoUsuario: TipoUsuario = TipoUsuario.CLIENTE,
    val estacionamento: Estacionamento?,
    val ativo: Boolean
)
