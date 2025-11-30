package com.example.imparkapk.data.dao.model

import java.util.Date

data class Gerente(
    val id: String = "",
    val usuarioId: String? = "",
    val nome: String,
    val email: String,
    val telefone: String,

    val estacionamentoId: String = "",
    val cpf: String,
    val nivelAcesso: Int = 1, // 1-Gerente
    val ativo: Boolean = true,
    val dataCriacao: Date = Date(),
    val dataAtualizacao: Date = Date(),
    val senha: String

)


