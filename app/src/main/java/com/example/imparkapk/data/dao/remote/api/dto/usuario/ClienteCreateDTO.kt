package com.example.imparkapk.data.dao.remote.api.dto.usuario

import java.time.LocalDate

data class ClienteCreateDTO(
    val nome: String,
    val email: String,
    val cpf: String,
    val telefone: String? = null,
    val dataNascimento: LocalDate? = null,
    val endereco: String? = null,
    val cep: String? = null,
    val cidade: String? = null,
    val estado: String? = null,
    val senha: String
)
data class ClienteUpdateDTO(
    val nome: String? = null,
    val email: String? = null,
    val cep: String? = null,
    val cidade: String? = null,
    val estado: String? = null,
    val fotoPerfil:String? = null,
    val notificacoesAtivas:Boolean? = null
)
