package com.example.imparkapk.UiState

data class AvaliacaoUiState(
    // Dados da avaliação
    val id: String = "",
    val idReserva: String = "",
    val idUsuario: String = "",
    val idEstabelecimento: String = "",
    val nota: Float = 0f,
    val comentario: String = "",
    val dataAvaliacao: String = "",

    // Categorias de avaliação (opcional)
    val notaComida: Float = 0f,
    val notaServico: Float = 0f,
    val notaAmbiente: Float = 0f,

    // Estado da UI
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
    val isDataValid: Boolean = false,

    // Validações
    val notaError: String? = null,
    val comentarioError: String? = null,

    // Navegação
    val shouldNavigateBack: Boolean = false,
    val shouldShowSuccessDialog: Boolean = false,

    // Flags de interação
    val isSubmitting: Boolean = false,
    val hasUserRated: Boolean = false
)