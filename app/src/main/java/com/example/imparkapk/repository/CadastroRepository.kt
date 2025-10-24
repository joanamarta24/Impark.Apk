package com.example.imparktcc.repository

import com.example.imparktcc.model.Carro
import com.example.imparktcc.model.Usuario
import kotlinx.coroutines.delay



class CadastroRepository {
    suspend fun cadastrarUsuario(usuario: Usuario): Boolean{
        // Simula chamada API/banco de dados
        delay(2000)
        return (0.9).random!=0  // 90% de chance de sucesso
    }
    suspend fun cadastrarCarro(carro: Carro): Boolean{
        // Simula chamada API/banco de dados
        delay(1500)
        return (0..9).random() != 0 // 90% de chance de sucesso
    }
    fun validarEmail(email: String): Boolean {
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$")
        return emailRegex.matches(email) && email.isNotEmpty()
    }

    fun validarSenha(senha: String): Boolean {
        return senha.length >= 6 && senha.any { it.isDigit() } && senha.any { it.isLetter() }
    }
}