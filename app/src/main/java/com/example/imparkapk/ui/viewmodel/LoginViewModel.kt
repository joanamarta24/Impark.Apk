package com.example.imparkapk.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Insert
import com.example.imparkapk.UiState.LoginUiState
import com.example.imparkapk.data.dao.repository.UsuarioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, emailValido = true) }
    }

    fun onSenhaChange(senha: String) {
        _uiState.update { it.copy(senha = senha) }
    }

    fun onLembrarMeChange(lembrar: Boolean) {
        _uiState.update { it.copy(lembrarMe = lembrar) }
    }

    fun login(onSuccess: () -> Unit = {}) {
        if (validarCampos()) {
            _uiState.update { it.copy(isLoading = true, mensagemErro = "") }

            viewModelScope.launch {
                val resultado = usuarioRepository.loginUsuario(_uiState.value.email, _uiState.value.senha)

                resultado.fold(
                    onSuccess = { usuario ->
                        _uiState.update { it.copy(
                            isLoading = false,
                            loginSucesso = true,
                            usuarioLogado = usuario
                        ) }
                        onSuccess()
                    },
                    onFailure = { erro ->
                        _uiState.update { it.copy(
                            isLoading = false,
                            mensagemErro = erro.message ?: "Erro ao fazer login"
                        ) }
                    }
                )
            }
        }
    }