package com.example.imparkapk.data.dao.converters.menager

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.imparkapk.UiState.UsuarioUiState
import com.example.imparktcc.model.Usuario
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.emptyList

@Singleton
class UsuarioStateManager @Inject constructor(){
    private val _uiState = mutableStateOf(UsuarioUiState())
    val uiState: State<UsuarioUiState> = _uiState

    fun updateField(campo: String, valor: String, usuarios: List<Usuario> = emptyList()) {
        // Implementação do gerenciamento de estado
    }

    fun setLoading(loading: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = loading)
    }

    fun setMensagemErro(mensagem: String) {
        _uiState.value = _uiState.value.copy(mensagemErro = mensagem)
    }

}