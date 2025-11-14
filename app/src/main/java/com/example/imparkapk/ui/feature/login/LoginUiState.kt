package com.example.imparkapk.ui.feature.login

data class LoginUiState (
    var email: String = "",
    var senha: String = "",
    var error: Boolean = false,
    var loading: Boolean = false
)