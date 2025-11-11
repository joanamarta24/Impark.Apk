package com.example.imparkapk.data.remote.api.response



data class LoginResponse(
    val token: String,
    val usuario: UsuarioResponse,
    val expiresIn: Long
)
data class UsuarioResponse(
    val id: String,
    val nome: String,
    val email: String,
    val tipo: String
)
