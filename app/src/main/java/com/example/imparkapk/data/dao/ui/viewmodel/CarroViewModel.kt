package com.example.imparkapk.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imparkapk.data.dao.remote.api.repository.carro.CarroRepository
import com.example.imparkapk.domain.model.Carro
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.onFailure

@HiltViewModel
class CarrosViewModel @Inject constructor(
    private val carroRepository: CarroRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CarrosUiState())
    val uiState: StateFlow<CarrosUiState> = _uiState.asStateFlow()

    init {
        carregarCarros()
    }

    fun carregarCarros() {
        _uiState.update { it.copy(isLoading = true, mensagemErro = null) }

        viewModelScope.launch {
            carroRepository.obterTodosCarros
                .onSuccess { carros ->
                    _uiState.update { state ->
                        state.copy(
                            carros = carros,
                            isLoading = false
                        )
                    }
                }
                .onFailure { erro ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            mensagemErro = "Erro ao carregar carros: ${erro.message}"
                        )
                    }
                }
        }
    }

    fun adicionarCarro(carro: Carro, onComplete: (Boolean) -> Unit = {}) {
        _uiState.update {
            it.copy(
                isLoading = true,
                mensagemErro = null,
                operacaoSucesso = null
            )
        }

        viewModelScope.launch {
            carroRepository.salvarCarro(carro)
                .onSuccess { carroSalvo ->
                    // Atualizar a lista localmente em vez de recarregar tudo
                    val novaLista = _uiState.value.carros + carroSalvo
                    _uiState.update { state ->
                        state.copy(
                            carros = novaLista,
                            operacaoSucesso = "Carro adicionado com sucesso!",
                            isLoading = false
                        )
                    }
                    onComplete(true)
                }
                .onFailure { erro ->
                    _uiState.update { state ->
                        state.copy(
                            mensagemErro = "Erro ao adicionar carro: ${erro.message}",
                            isLoading = false
                        )
                    }
                    onComplete(false)
                }
        }
    }

    fun atualizarCarro(carro: Carro, onComplete: (Boolean) -> Unit = {}) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                val resultado = carroRepository.atualizarCarro(carro)
                resultado.onSuccess { carroAtualizado ->
                    // Atualizar a lista local
                    val carrosAtualizados = _uiState.value.carros.map {
                        if (it.id == carro.id) carroAtualizado else it
                    }
                    _uiState.update { state ->
                        state.copy(
                            carros = carrosAtualizados,
                            operacaoSucesso = "Carro atualizado com sucesso!",
                            mensagemErro = null,
                            isLoading = false
                        )
                    }
                    onComplete(true)
                }.onFailure { erro ->
                    _uiState.update { state ->
                        state.copy(
                            mensagemErro = "Erro ao atualizar carro: ${erro.message}",
                            isLoading = false
                        )
                    }
                    onComplete(false)
                }
            } catch (e: Exception) {
                _uiState.update { state ->
                    state.copy(
                        mensagemErro = "Erro inesperado: ${e.message}",
                        isLoading = false
                    )
                }
                onComplete(false)
            }
        }
    }

    fun excluirCarro(carroId: String, onComplete: (Boolean) -> Unit = {}) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                val resultado = carroRepository.deletarCarro(carroId) // Método corrigido
                resultado.onSuccess { sucesso ->
                    if (sucesso) {
                        // Remover da lista local
                        val carrosAtualizados = _uiState.value.carros.filter { it.id != carroId }
                        _uiState.update { state ->
                            state.copy(
                                carros = carrosAtualizados,
                                operacaoSucesso = "Carro excluído com sucesso!",
                                mensagemErro = null,
                                isLoading = false
                            )
                        }
                        onComplete(true)
                    } else {
                        _uiState.update { state ->
                            state.copy(
                                mensagemErro = "Erro ao excluir carro",
                                isLoading = false
                            )
                        }
                        onComplete(false)
                    }
                }.onFailure { erro ->
                    _uiState.update { state ->
                        state.copy(
                            mensagemErro = "Erro ao excluir carro: ${erro.message}",
                            isLoading = false
                        )
                    }
                    onComplete(false)
                }
            } catch (e: Exception) {
                _uiState.update { state ->
                    state.copy(
                        mensagemErro = "Erro inesperado: ${e.message}",
                        isLoading = false
                    )
                }
                onComplete(false)
            }
        }
    }

    fun definirComoPrincipal(carroId: String, onComplete: (Boolean) -> Unit = {}) {
        viewModelScope.launch {
            try {
                // Primeiro, atualizar todos os carros para não principais
                val carrosParaAtualizar = _uiState.value.carros.map { carro ->
                    if (carro.id == carroId) {
                        carro.copy(principal = true)
                    } else {
                        carro.copy(principal = false)
                    }
                }

                // Atualizar cada carro no repositório
                var todasAtualizacoesSucederam = true
                carrosParaAtualizar.forEach { carro ->
                    val resultado = carroRepository.atualizarCarro(carro)
                    if (resultado.isFailure) {
                        todasAtualizacoesSucederam = false
                    }
                }

                if (todasAtualizacoesSucederam) {
                    _uiState.update { state ->
                        state.copy(
                            carros = carrosParaAtualizar,
                            operacaoSucesso = "Carro definido como principal!",
                            mensagemErro = null
                        )
                    }
                    onComplete(true)
                } else {
                    _uiState.update { state ->
                        state.copy(
                            mensagemErro = "Erro ao definir carro como principal"
                        )
                    }
                    onComplete(false)
                }
            } catch (e: Exception) {
                _uiState.update { state ->
                    state.copy(
                        mensagemErro = "Erro inesperado: ${e.message}"
                    )
                }
                onComplete(false)
            }
        }
    }

    fun buscarCarroPorId(carroId: String): Carro? {
        return _uiState.value.carros.find { it.id == carroId }
    }

    fun selecionarCarro(carro: Carro?) {
        _uiState.update { it.copy(carroSelecionado = carro) }
    }

    fun limparMensagens() {
        _uiState.update { state ->
            state.copy(
                mensagemErro = null,
                operacaoSucesso = null
            )
        }
    }

    fun validarCarro(carro: Carro): ValidationResult {
        val errors = mutableListOf<String>()

        if (carro.placa.isBlank()) {
            errors.add("Placa é obrigatória")
        } else if (!validarPlaca(carro.placa)) {
            errors.add("Placa inválida")
        }

        if (carro.marca.isBlank()) {
            errors.add("Marca é obrigatória")
        }

        if (carro.modelo.isBlank()) {
            errors.add("Modelo é obrigatório")
        }

        if (carro.cor.isBlank()) {
            errors.add("Cor é obrigatória")
        }

        if (carro.ano < 1900 || carro.ano > 2100) {
            errors.add("Ano inválido")
        }

        return ValidationResult(
            isValid = errors.isEmpty(),
            errors = errors
        )
    }

    private fun validarPlaca(placa: String): Boolean {
        // Validação para placas no formato Mercosul e antigo
        val placaMercosulRegex = Regex("^[A-Z]{3}[0-9][A-Z][0-9]{2}$")
        val placaAntigaRegex = Regex("^[A-Z]{3}[0-9]{4}$")
        val placaUpper = placa.uppercase().replace("\\s".toRegex(), "")

        return placaMercosulRegex.matches(placaUpper) || placaAntigaRegex.matches(placaUpper)
    }

    data class CarrosUiState(
        val carros: List<Carro> = emptyList(),
        val isLoading: Boolean = false,
        val mensagemErro: String? = null,
        val operacaoSucesso: String? = null,
        val carroSelecionado: Carro? = null
    )

    data class ValidationResult(
        val isValid: Boolean,
        val errors: List<String>
    ) {
        val errorMessage: String?
            get() = if (errors.isNotEmpty()) errors.joinToString(", ") else null
    }
}