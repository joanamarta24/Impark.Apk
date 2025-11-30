package com.example.imparktapk.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imparkapk.UiState.LoginUiState
import com.example.imparkapk.data.dao.remote.api.api.usuarios.ClienteApi
import com.example.imparkapk.data.dao.remote.api.repository.usuario.ClienteRepository
import com.example.imparkapk.data.dao.remote.api.request.LoginRequest
import com.example.imparkapk.data.dao.local.dao.TokenStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val clienteRepository: ClienteRepository,
    private val clienteApi: ClienteApi,
    private val tokenManager: TokenStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    // Eventos para navegação
    private val _navigateToHome = MutableStateFlow(false)
    val navigateToHome: StateFlow<Boolean> = _navigateToHome.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.update { currentState ->
            currentState.copy(
                email = email,
                emailValido = clienteRepository.validarEmail(email)
            )
        }
        limparErros()
    }

    fun onSenhaChange(senha: String) {
        _uiState.update { it.copy(senha = senha) }
        limparErros()
    }

    fun onLembrarMeChange(lembrar: Boolean) {
        _uiState.update { it.copy(lembrarMe = lembrar) }
    }

    fun login() {
        if (!validarCampos()) {
            _uiState.update {
                it.copy(mensagemErro = "Preencha todos os campos corretamente")
            }
            return // CORREÇÃO: Adicionar return para sair da função
        }

        _uiState.update { it.copy(isLoading = true, mensagemErro = "") }

        viewModelScope.launch {
            try {
                val request = LoginRequest(
                    email = _uiState.value.email,
                    senha = _uiState.value.senha
                )
                val response = clienteApi.login(request)

                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    val token = loginResponse?.token

                    if (token != null) {
                        tokenManager.saveToken(token)
                        _uiState.update { it.copy(isLoading = false) }
                        _navigateToHome.value = true // Navegar para home
                    } else {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                mensagemErro = "Token não recebido"
                            )
                        }
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            mensagemErro = "Erro no login: ${response.message()}"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        mensagemErro = "Erro: ${e.message}"
                    )
                }
            }
        }
    }

    // CORREÇÃO: Remover método duplicado ou renomear
    fun loginComCredenciais(email: String, senha: String) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }

                val response = clienteApi.login(LoginRequest(email, senha))
                if (response.isSuccessful) {
                    val token = response.body()?.token
                    token?.let {
                        tokenManager.saveToken(it)
                        _uiState.update { it.copy(isLoading = false) }
                        _navigateToHome.value = true
                    } ?: run {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                mensagemErro = "Token não recebido"
                            )
                        }
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            mensagemErro = "Erro: ${response.message()}"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        mensagemErro = "Erro: ${e.message}"
                    )
                }
            }
        }
    }

    fun logout() {
        tokenManager.clearToken()
        _uiState.value = LoginUiState() // Resetar estado
        _navigateToHome.value = false // Resetar navegação
    }

    private fun validarCampos(): Boolean {
        val emailValido = clienteRepository.validarEmail(_uiState.value.email)
        val senhaPreenchida = _uiState.value.senha.isNotBlank()

        _uiState.update { it.copy(emailValido = emailValido) }

        return emailValido && senhaPreenchida
    }

    fun limparErros() {
        _uiState.update { it.copy(mensagemErro = "") }
    }

    fun resetState() {
        _uiState.value = LoginUiState()
        _navigateToHome.value = false
    }

    // Método para resetar a navegação após uso
    fun onNavigationComplete() {
        _navigateToHome.value = false
    }
}