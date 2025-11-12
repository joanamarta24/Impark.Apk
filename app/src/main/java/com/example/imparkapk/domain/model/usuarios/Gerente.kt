package com.example.imparkapk.domain.model.usuarios

import com.example.imparkapk.domain.model.Estacionamento
import com.example.imparkapk.domain.model.enuns.TipoDeUsuario
import java.util.Date

data class Gerente(
    val id: Long,
    val nome: String,
    val email: String,
    val senha: String,
    val telefone: String,
    val dataNascimento: Date,
    val tipoUsuario: TipoDeUsuario = TipoDeUsuario.CLIENTE,
    val estacionamento: Estacionamento?,
    val updatedAt: Long,
    val ativo: Boolean
)