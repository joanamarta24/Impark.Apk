package com.example.imparktcc.model



enum class TipoUsuario {
    CLIENTE,
    ESTACIONAMENTO,
    GERENTE,
    DONO,
    DESENVOLVELDOR

}
data class Usuario(
    val id: String ="",
    val nome: String ="",
    val email: String ="",
    val senha: String ="",
    val tipo: String,

    val estacionamentoId: String? = null
)