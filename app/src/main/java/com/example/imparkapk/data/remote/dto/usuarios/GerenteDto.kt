package com.example.imparkapk.data.remote.dto.usuarios

import com.example.imparkapk.domain.model.enuns.TipoDeUsuario
import java.util.Date

data class GerenteDto(
    val id: Long,
    val nome: String,
    val email: String,
    val senha: String,
    val telefone: String,
    val dataNascimento: Date,
    val tipoUsuario: TipoDeUsuario = TipoDeUsuario.CLIENTE,
    val estacionamentoId: Long,
    val ativo: Boolean
)
