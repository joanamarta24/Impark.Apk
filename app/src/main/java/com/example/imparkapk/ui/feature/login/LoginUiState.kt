package com.example.imparkapk.ui.feature.login

data class LoginUiState (
    var email: String = "",
    var senha: String = "",

    // Informações inatas da tela

    var error: String? = null,
    var loading: Boolean = false
)