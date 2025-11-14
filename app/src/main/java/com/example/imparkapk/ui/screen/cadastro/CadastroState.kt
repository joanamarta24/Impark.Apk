package com.example.imparktcc.cadastro

data class adastroUIState (
    val email: String ="",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage : String? = null,
    val isCadastroSuccessful: Boolean = false

)