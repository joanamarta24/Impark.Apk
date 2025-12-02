package com.example.imparkapk.data.dao.ui.Screen

import com.example.imparkapk.data.dao.model.enus.TipoUsuario
import com.example.imparkapk.domain.model.Carro


data class ClienteUiState(
    // Dados do usuário
    val usuario: Cliente? = null,
    val veiculos: List<Carro> = emptyList(),

    // Estados de carregamento
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isLoadingVeiculos: Boolean = false,

    // Estados de erro
    val errorMessage: String? = null,
    val veiculosErrorMessage: String? = null,

    // Estados de sucesso
    val successMessage: String? = null,
    val isEditMode: Boolean = false,
    val isUpdating: Boolean = false,

    // Formulário de edição
    val nome: String = "",
    val email: String = "",
    val telefone: String = "",
    val cpf: String = "",
    val senhaAtual: String = "",
    val novaSenha: String = "",
    val confirmarSenha: String = "",

    // Validações
    val nomeError: String? = null,
    val emailError: String? = null,
    val telefoneError: String? = null,
    val cpfError: String? = null,
    val senhaAtualError: String? = null,
    val novaSenhaError: String? = null,
    val confirmarSenhaError: String? = null,

    // Estados de navegação
    val shouldNavigateToVeiculos: Boolean = false,
    val shouldNavigateToReservas: Boolean = false,
    val shouldNavigateToAvaliacoes: Boolean = false,
    val shouldNavigateToLogin: Boolean = false,
    val shouldShowLogoutDialog: Boolean = false,
    val shouldShowDeleteDialog: Boolean = false,
    val shouldShowSuccessDialog: Boolean = false,

    // Estatísticas
    val totalReservas: Int = 0,
    val totalAvaliacoes: Int = 0,
    val carroPrincipal: Carro? = null,

    // Filtros
    val filtroVeiculosAtivos: Boolean = true
)

// Modelo de Usuario
data class Cliente(
    val id: String,
    val nome: String,
    val email: String,
    val telefone: String,
    val cpf: String,
    val tipo: TipoUsuario,
    val dataCriacao: String,
    val dataAtualizacao: String,
    val ativo: Boolean,
    val avatarUrl: String? = null,
    val preferencias: PreferenciasUsuario = PreferenciasUsuario()
)


data class PreferenciasUsuario(
    val receberNotificacoes: Boolean = true,
    val receberPromocoes: Boolean = false,
    val temaEscuro: Boolean = false,
    val idioma: String = "pt-BR"
)