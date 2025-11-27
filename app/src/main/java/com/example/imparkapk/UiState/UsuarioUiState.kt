package com.example.imparkapk.UiState


import com.example.imparktcc.model.Cliente


data class ClienteUiState(

    // Dados do usuário
    val usuario: Cliente? = null,
    val usuarios:List<Cliente> = emptyList(),

    // Formulários
    val nome:String ="",
    val email:String ="",
    val senha: String ="",
    val confirmaSenha: String ="",
    val tipoUsuario: String ="CLIENTE",

    // Estados de loadin
    val isLoading: Boolean = false,
    val isLogging: Boolean = false,
    val isRegistering: Boolean = false,
    val loginSucesso: Boolean = false,
    val cadastroSucesso: Boolean = false,
    val mensagemErro: String = "",
    val mensagemSucesso: String = "",
    val emailValido: Boolean = true,
    val senhaValida: Boolean = true,
    val searchQuery: String = "",
    val usuariosFiltrados: List<Cliente> = emptyList(),
    val usuarioLogado: Cliente? = null,
    val sessaoAtiva: Boolean = false
)
