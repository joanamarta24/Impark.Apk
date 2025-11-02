package com.example.imparkapk.UiState

data class LoginUiState(
    val email: String = "",
    val senha: String = "",
    val lembrarMe: Boolean = false,
    val isLoading: Boolean = false,
    val loginSucesso: Boolean = false,
    val emailValido: Boolean = true,
    val mensagemErro: String = ""
) {
    val botaoLoginHabilitado: Boolean
        get() = email.isNotBlank() && senha.isNotBlank() && !isLoading
}
