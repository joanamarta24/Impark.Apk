package com.example.imparkapk.ui.feature.register

import com.example.imparkapk.domain.model.enuns.TipoDeUsuario

data class RegisterUiState (
    var nome: String = "",
    var email: String = "",
    var senha: String = "",
    var confirmarSenha: String = "",
    var tipoDeUsuario: TipoDeUsuario = TipoDeUsuario.CLIENTE,
    var isLoading: Boolean = false,
    var errorMessage: String? = null
)