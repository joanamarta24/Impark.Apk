package com.example.imparktcc.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imparkapk.data.dao.model.Estacionamento
import com.example.imparkapk.data.dao.remote.api.repository.EstacionamentoRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.sortedBy
import kotlin.collections.sortedByDescending

@HiltViewModel
class EstacionamentoViewModel @Inject constructor(
    private val estacionamentoRepository: EstacionamentoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(EstacionamentoUiState())
    val uiState: StateFlow<EstacionamentoUiState> = _uiState.asStateFlow()

    init {
        carregarEstacionamentos()
    }

    fun carregarEstacionamentos() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                val estacionamentos = estacionamentoRepository.listarEstacionamentos()
                _uiState.update { it.copy(
                    isLoading = false,
                    estacionamentos = estacionamentos,
                    estacionamentosFiltrados = estacionamentos
                ) }
            } catch (e: Exception) {
                _uiState.update { it.copy(
                    isLoading = false,
                    mensagemErro = "Erro ao carregar estacionamentos"
                ) }
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { currentState ->
            val filtrados = if (query.isBlank()) {
                currentState.estacionamentos
            } else {
                currentState.estacionamentos.filter { estacionamento ->
                    estacionamento.nome.contains(query, ignoreCase = true) ||
                            estacionamento.endereco.contains(query, ignoreCase = true)
                }
            }

            currentState.copy(
                searchQuery = query,
                estacionamentosFiltrados = filtrados
            )
        }
    }

    fun onFiltroVagasChange(filtrarComVagas: Boolean) {
        _uiState.update { currentState ->
            val filtrados = if (filtrarComVagas) {
                currentState.estacionamentos.filter { it.vagasDisponiveis > 0 }
            } else {
                currentState.estacionamentos
            }

            currentState.copy(
                filtrarComVagas = filtrarComVagas,
                estacionamentosFiltrados = filtrados
            )
        }
    }

    fun onOrdenacaoChange(ordenacao: OrdenacaoEstacionamento) {
        _uiState.update { currentState ->
            val ordenados = when (ordenacao) {
                OrdenacaoEstacionamento.MENOR_PRECO -> {
                    currentState.estacionamentosFiltrados.sortedBy { it.valorHora }
                }
                OrdenacaoEstacionamento.MAIOR_PRECO -> {
                    currentState.estacionamentosFiltrados.sortedByDescending { it.valorHora }
                }
                OrdenacaoEstacionamento.MAIS_VAGAS -> {
                    currentState.estacionamentosFiltrados.sortedByDescending { it.vagasDisponiveis }
                }
                OrdenacaoEstacionamento.MAIS_PROXIMO -> {
                    currentState.estacionamentosFiltrados // Ordenação por proximidade seria implementada com GPS
                }
            }

            currentState.copy(
                ordenacaoSelecionada = ordenacao,
                estacionamentosFiltrados = ordenados
            )
        }
    }

    fun selecionarEstacionamento(estacionamento: Estacionamento) {
        _uiState.update { it.copy(estacionamentoSelecionado = estacionamento) }
    }

    fun limparMensagens() {
        _uiState.update { it.copy(mensagemErro = "", mensagemSucesso = "") }
    }

    fun buscarEstacionamentoPorId(id: String, onSuccess: (Estacionamento) -> Unit = {}) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                val estacionamento = estacionamentoRepository.buscarEstacionamentoPorId(id)
                if (estacionamento != null) {
                    _uiState.update { it.copy(
                        isLoading = false,
                        estacionamentoSelecionado = estacionamento
                    ) }
                    onSuccess(estacionamento)
                } else {
                    _uiState.update { it.copy(
                        isLoading = false,
                        mensagemErro = "Estacionamento não encontrado"
                    ) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(
                    isLoading = false,
                    mensagemErro = "Erro ao buscar estacionamento"
                ) }
            }
        }
    }
}

data class EstacionamentoUiState(
    val estacionamentos: List<Estacionamento> = emptyList(),
    val estacionamentosFiltrados: List<Estacionamento> = emptyList(),
    val estacionamentoSelecionado: Estacionamento? = null,
    val searchQuery: String = "",
    val filtrarComVagas: Boolean = false,
    val ordenacaoSelecionada: OrdenacaoEstacionamento = OrdenacaoEstacionamento.MENOR_PRECO,
    val isLoading: Boolean = false,
    val mensagemErro: String = "",
    val mensagemSucesso: String = ""
)

enum class OrdenacaoEstacionamento {
    MENOR_PRECO, MAIOR_PRECO, MAIS_VAGAS, MAIS_PROXIMO
}