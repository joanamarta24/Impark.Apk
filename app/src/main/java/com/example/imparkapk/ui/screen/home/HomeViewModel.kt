package com.example.imparkapk.ui.screen.home

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imparkapk.data.repository.EstacionamentoRepository
import com.example.imparkapk.domain.model.Estacionamento
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// Adicionamos a injeção de dependência com Hilt.
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val estacionamentoRepository: EstacionamentoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        carregarEstacionamentos()
    }

    /**
     * Busca os dados do repositório e atualiza o estado da UI.
     */
    fun carregarEstacionamentos() {
        viewModelScope.launch {
            // 1. Inicia o estado de carregamento
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                // 2. Busca os dados do repositório (a fonte de verdade)
                val estacionamentosDoDominio = estacionamentoRepository.getEstacionamentosFlow()

                // 3. Mapeia a lista do modelo de domínio para o modelo de UI
                val estacionamentosUi = estacionamentosDoDominio.map { estacionamento ->
                    mapToUiModel(estacionamento)
                }

                // 4. Atualiza o estado com sucesso
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        estacionamentos = estacionamentosUi
                    )
                }

            } catch (e: Exception) {
                // 5. Em caso de erro, atualiza o estado com uma mensagem
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Falha ao carregar estacionamentos."
                    )
                }
            }
        }
    }

    /**
     * Mapeia o modelo de dados do domínio (Estacionamento) para o modelo de UI.
     * Esta função é privada e centraliza a lógica de apresentação.
     */
    private fun mapToUiModel(estacionamento: Estacionamento): EstacionamentoUiModel {
        val vagasDisponiveis = estacionamento.vagasDisponiveis
        val textoVagas: String
        val corVagas: Color

        if (vagasDisponiveis > 0) {
            textoVagas = "$vagasDisponiveis vagas disponíveis"
            corVagas = Color.Green
        } else {
            textoVagas = "Lotado"
            corVagas = Color.Red
        }

        return EstacionamentoUiModel(
            id = estacionamento.id,
            nome = estacionamento.nome,
            endereco = estacionamento.endereco,
            vagasDisponiveis = vagasDisponiveis,
            textoVagas = textoVagas,
            corVagas = corVagas.value.toLong()
        )
    }
}