package com.example.imparktcc.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imparkapk.UiState.LoginUiState
import com.example.imparkapk.data.dao.remote.api.api.UsuarioApi
import com.example.imparkapk.data.dao.remote.api.repository.usuario.UsuarioRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository,
    private val usuarioApi: UsuarioApi,
    private val tokenManager: TokenManager
) : ViewModel() {


    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    // Eventos para navegaçã
    private val _navigateToHome = MutableStateFlow(false)
    val navigateToHome: StateFlow<Boolean> = _navigateToHome.asStateFlow()


    fun onEmailChange(email: String) {
        _uiState.update { currentState ->
            currentState.copy(
                email = email,
                emailValido = usuarioRepository.validarEmail(email)
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
    fun login(){
        if (!validarCampos()){
            _uiState.update {
                it.copy(mensagemErro = "Preencha todos os campos corretamente")
                return
            }
        _uiState.update { it.copy(isLoading = true,  mensagemErro = "") }
            viewModelScope.launch {
                try {
                    val request = LoginRequest(
                        email = _uiState.value.email,
                        senha = _uiState.value.senha
                    )
                    val response = usuarioApi.login(request)
                    if (response.isSuccessful){
                        val loginResponse = response.body()
                        val token = loginResponse?.token
                        if (token != null){
                            tokenManager.saveToken(token)
                        }
                    }
                }
            }
    }


    fun login(email: String, senha: String) {
        viewModelScope.launch {
            try {
                val response = usuarioApi.login(LoginRequest(email, senha))
                if (response.isSuccessful) {
                    val token = response.body()?.token
                    token?.let { tokenManager.saveToken(it) }

                }
            } catch (e: Exception) {

            }
        }
    }

    fun logout() {
        tokenManager.clearToken()

    }
}
    private fun validarCampos(): Boolean {
        val emailValido = usuarioRepository.validarEmail(_uiState.value.email)
        val senhaPreenchida = _uiState.value.senha.isNotBlank()

        _uiState.update { it.copy(emailValido = emailValido) }

        return emailValido && senhaPreenchida
    }

    fun limparErros() {
        _uiState.update { it.copy(mensagemErro = "") }
    }

    fun resetState() {
        _uiState.value = LoginUiState()
    }
}

