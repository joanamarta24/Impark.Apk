package com.example.imparktcc.model
enum class {
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
    val tipo:TipoUsuario,

    val estacionamentoId:String? = null
)