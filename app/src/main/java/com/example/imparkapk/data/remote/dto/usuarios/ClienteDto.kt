package com.example.imparkapk.data.remote.dto.usuarios

import com.example.imparkapk.domain.model.enuns.TipoDeUsuario
import java.util.Date

data class ClienteDto(
    val id: Long,
    val nome: String,
    val email: String,
    val senha: String?,
    val dataNascimento: Date,
    val tipoUsuario: TipoDeUsuario = TipoDeUsuario.CLIENTE,
    val carros: List<Long> = listOf(),
    val avaliacoes: List<Long> = listOf(),
    val reservas: List<Long> = listOf(),
    val updatedAt: Long,
    val ativo: Boolean
)
