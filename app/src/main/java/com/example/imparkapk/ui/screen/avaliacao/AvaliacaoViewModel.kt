package com.example.imparkapk.ui.screen.avaliacao

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imparkapk.domain.model.Avaliacao
import com.example.imparkapk.data.local.remote.api.repository.avaliacao.AvaliacaoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AvaliacaoViewModel @Inject constructor(
    private  val avaliacaoRepository: AvaliacaoRepository

): ViewModel() {
    private val _uiState = MutableStateFlow(AvaliacaoUiState())
    val uiState: StateFlow<AvaliacaoUiState> = _uiState.asStateFlow()

    fun onNotaChange(nota: Int) {
        _uiState.update { it.copy(notaSelecionada = nota) }
    }

    fun onComentarioChange(comentario: String) {
    }

    fun criarAvaliacao(usuarioId: String, estacionamentoId: String, onSuccess: () -> Unit = {}) {
        if (validarAvaliacao()) {
            _uiState.update { it.copy(isLoading = true, mensagemErro = "") }

            viewModelScope.launch {
                try {
                    val avaliacao = Avaliacao(
                        usuarioId = usuarioId,
                        estacionamentoId = estacionamentoId,
                        nota = _uiState.value.notaSelecionada,
                        comentario = _uiState.value.comentario,
                        dataAvaliacao = Date()
                    )

                    val sucesso = avaliacaoRepository.criarAvaliacao(avaliacao)

                    if (sucesso) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                avaliacaoCriada = true,
                                mensagemSucesso = "Avaliação enviada com sucesso!"
                            )
                        }
                        onSuccess()
                    } else {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                mensagemErro = "Erro ao enviar avaliação"
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
    }
}