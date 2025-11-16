package com.example.imparkapk.data.dao.local.dao.usuarios

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.imparkapk.data.dao.model.Estacionamento
import com.example.imparkapk.data.dao.model.enus.TipoDeUsuario
import java.util.Date

data class GerenteDao(
    val id: Long,
    val nome: String,
    val email: String,
    val senha: String,
    val telefone: String,
    val dataNascimento: Date,
    val tipoUsuario: TipoDeUsuario = TipoDeUsuario.CLIENTE,
    val estacionamento: Estacionamento?,
    val ativo: Boolean
)
