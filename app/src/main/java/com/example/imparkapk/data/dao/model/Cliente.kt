package com.example.imparkapk.model



data class Cliente(
    val id: String ="",
    val nome: String ="",
    val email: String ="",
    val senha: String ="",
    val tipo: String,

    val estacionamentoId: String? = null
)
