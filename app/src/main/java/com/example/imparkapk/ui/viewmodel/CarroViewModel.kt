package com.example.imparkapk.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imparktcc.data.repository.CarroRepository
import com.example.imparktcc.model.Carro
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CarroViewModel @Inject constructor(
    private val carroRepository:CarroRepository
):ViewModel() {
    private val _uiState = MutableStateFlow(CarroUiState())
    val uiState: StateFlow<CarroUiState> = _uiState.asSateFlow()

    fun onMModeliChange(modelo::String)
    {
        _uiState.update { it.copy(modelo = modelo, modeloValido = true) }
    }

    fun onPlacaChange(placa: String) {
        _uiState { it.copy(placa = placa.uppercase(), placaValida = true) }
    }

    fun onCorChange(cor: String) {
        _uiState.update { it.copy(cor = cor, corValida = true) }
    }

    fun onCorChange(cor: String) {
        _uiState.update { it.copy(cor = cor, corValida = true) }
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
                            mensagemErro = "Erro de conexão"
                        )
                    }
                }
            }
        }
        fun carregarCarroDoUsuario(usuarioId: String) {
            _uiState.update{it.copy(isLoadind = true)}
            viewModelScope.launch {
                try {

                    val carros = carroRepository.listarCarrosPorUsuario(usuarioId)
                    _uiState.update{it.copy(
                        isLoading = false,
                        carros = carros
                    )}
                }catch (e:Exception){
                    _uiState.update{it.cpy(
                        isLoanding = false,
                        mensagemErro="Erro ao carregar carros"
                    )}
                }
            }
        }
    }
    fun selecionarCArro(carro: Carro){
        _uiState.update{it.copy(carroSelecionado = carro)}
    }
    fun editarCarro(carro: Carro, onSuccess: () -> Unit = {}) {
        _uiState.update { it.copy(isLoading = true, mensagemErro = "") }

        viewModelScope.launch {
            try {
                val sucesso = carroRepository.atualizarCarro(carro)

                if (sucesso) {
                    _uiState.update { it.copy(
                        isLoading = false,
                        mensagemSucesso = "Carro atualizado com sucesso!"
                    ) }
                    onSuccess()
                } else {
                    _uiState.update { it.copy(
                        isLoading = false,
                        mensagemErro = "Erro ao atualizar carro"
                    ) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(
                    isLoading = false,
                    mensagemErro = "Erro de conexão"
                ) }
            }
        }
    }
    fun excluirCarro(carroId:String,onSuccess: () -> Unit = {}){
        _uiState.update{it.copy(isLoading = true,mensagemErro = "")}
        viewModelScope.launch {  }
    }
}
