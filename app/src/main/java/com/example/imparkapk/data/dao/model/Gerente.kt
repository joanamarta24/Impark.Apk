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
    val senha: String,
    val permissoesPersonalizadas: List<String> = emptyList()

){
    companion object{
        const val NIVEL_DONO =1
        const val NIVEL_GERENTE = 2
        const val NIVEL_SUPERVISOR = 3
        const val NIVEL_FUNCIONARIO = 4
    }
}


