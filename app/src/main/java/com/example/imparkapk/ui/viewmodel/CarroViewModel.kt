package com.example.imparkapk.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imparkapk.UiState.CarroUiState
import com.example.imparkapk.data.repository.CarroRepository
import com.example.imparkapk.domain.model.Carro
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarroViewModel @Inject constructor(
    private val carroRepository: CarroRepository

) : ViewModel() {

    private val _uiState = MutableStateFlow(CarroUiState())
    val uiState: StateFlow<CarroUiState> = _uiState.asStateFlow()

    fun onModeloChange(modelo: String) {
        _uiState.update { it.copy(modelo = modelo, modeloValido = true) }
    }

    fun onPlacaChange(placa: String) {
        _uiState.update { it.copy(placa = placa.uppercase(), placaValida = true) }
    }

    fun onCorChange(cor: String) {
        _uiState.update { it.copy(cor = cor, corValida = true) }
    }


    private fun validarCarro(): Boolean {
        val state = _uiState.value
        val modeloValido = state.modelo.isNotBlank()
        val placaValida = state.placa.isNotBlank() && state.placa.length >= 7
        val corValida = state.cor.isNotBlank()

        _uiState.update {
            it.copy(
                modeloValido = modeloValido,
                placaValida = placaValida,
                corValida = corValida
            )
        }

        return modeloValido && placaValida && corValida
    }

    fun cadastrarCarro(usuarioId: String, onSuccess: () -> Unit = {}) {
        if (validarCarro()) {
            _uiState.update { it.copy(isLoading = true, mensagemErro = "") }

            viewModelScope.launch {
                try {
                    val carro = Carro(
                        usuarioId = usuarioId,
                        modelo = _uiState.value.modelo,
                        placa = _uiState.value.placa,
                        cor = _uiState.value.cor
                    )

                    val sucesso = carroRepository.cadastrarCarro(carro)

                    if (sucesso) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                carroCadastrado = true,
                                mensagemSucesso = "Carro cadastrado com sucesso!"
                            )
                        }
                        onSuccess()
                    } else {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                mensagemErro = "Erro ao cadastrar carro"
                            )
                        }
                    }
                } catch (e: Exception) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            mensagemErro = "Erro de conexão: ${e.message}"
                        )
                    }
                }
            }
        } else {
            _uiState.update {
                it.copy(mensagemErro = "Preencha todos os campos corretamente")
            }
        }
    }

    fun carregarCarrosDoUsuario(usuarioId: String) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                val carros = carroRepository.listarCarrosPorUsuario(usuarioId)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        carros = carros
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        mensagemErro = "Erro ao carregar carros: ${e.message}"
                    )
                }
            }
        }
    }

    fun selecionarCarro(carro: Carro) {
        _uiState.update { it.copy(carroSelecionado = carro) }
    }

    fun editarCarro(carro: Carro, onSuccess: () -> Unit = {}) {
        _uiState.update { it.copy(isLoading = true, mensagemErro = "") }

        viewModelScope.launch {
            try {
                val sucesso = carroRepository.atualizarCarro(carro)

                if (sucesso) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            mensagemSucesso = "Carro atualizado com sucesso!"
                        )
                    }
                    onSuccess()
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            mensagemErro = "Erro ao atualizar carro"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        mensagemErro = "Erro de conexão: ${e.message}"
                    )
                }
            }
        }
    }

    fun excluirCarro(carroId: String, onSuccess: () -> Unit = {}) {
        _uiState.update { it.copy(isLoading = true, mensagemErro = "") }
        viewModelScope.launch {
            try {
                val sucesso = carroRepository.excluirCarro(carroId)
                if (sucesso) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            mensagemSucesso = "Carro excluído com sucesso!"
                        )
                    }
                    onSuccess()
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            mensagemErro = "Erro ao excluir carro"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        mensagemErro = "Erro de conexão: ${e.message}"
                    )
                }
            }
        }
    }

    fun limparMensagens() {
        _uiState.update {
            it.copy(
                mensagemErro = "",
                mensagemSucesso = ""
            )
        }
    }

    fun resetState() {
        _uiState.value = CarroUiState()
    }
}