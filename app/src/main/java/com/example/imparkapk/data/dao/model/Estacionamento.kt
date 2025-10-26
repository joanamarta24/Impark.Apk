package com.example.imparkapk.data.dao.model

data class Estacionamento(
    val id: String = "",
    val nome: String = "",
    val endereco: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val totalVagas: Int = 0,
    val vagasDisponiveis: Int = 0,
    val valorHora: Double = 0.0,
    val telefone: String = "",
    val horarioAbertura: String = "",
    val horarioFechamento: String = "",
    val ativo: Boolean = true
) {
    val estaAberto: Boolean
        get() {

            return true
        }

    val notaMedia: Double = 0.0
    val totalAvaliacoes: Int = 0
}
