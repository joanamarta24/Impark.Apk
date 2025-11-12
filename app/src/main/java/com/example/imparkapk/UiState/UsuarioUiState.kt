package com.example.imparkapk.UiState


import com.example.imparktcc.model.Usuario

data class UsuarioUiState(

    // Dados do usuário
    val usuario: Usuario? = null,
    val usuarios:List<Usuario> = emptyList(),

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
    val usuariosFiltrados: List<Usuario> = emptyList(),
    val usuarioLogado: Usuario? = null,
    val sessaoAtiva: Boolean = false
)
