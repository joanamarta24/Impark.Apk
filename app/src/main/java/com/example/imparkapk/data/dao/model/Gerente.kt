package com.example.imparkapk.data.dao.model

data class Gerente(
    val id: String = "",
    val usuarioId: String = "",
    val estacionamentoId: String = "",
    val nivelAcesso: Int = 1, // 1-Gerente, 2-Supervisor, 3-Funcionário
    val ativo: Boolean = true,
    val dataCriacao: String,
    val dataAtualizacao: String
) {

}
