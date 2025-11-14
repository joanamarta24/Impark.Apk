package com.example.imparkapk.ui.screen.home
/**
 * Representa os dados de um estacionamento formatados para a camada de UI.
 */
data class EstacionamentoUiModel(
    val id: Long,
    val nome: String,
    val endereco: String,
    val vagasDisponiveis: Int,
    val textoVagas: String,
    val corVagas: Long
)

/**
 * Define o estado completo da HomeScreen.
 */
data class HomeUiState(
    val estacionamentos: List<EstacionamentoUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)