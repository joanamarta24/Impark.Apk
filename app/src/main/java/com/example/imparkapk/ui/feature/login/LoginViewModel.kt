package com.example.imparkapk.ui.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imparkapk.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LoginUiState())
    val _uiState: StateFlow<LoginUiState> = _state

    fun onSenhaChange(newPassword: String) {
        _state.value = _state.value.copy(senha = newPassword)
    }

    fun onEmailChange(newEmail: String) {
        _state.value = _state.value.copy(email = newEmail)
    }

    fun login(onLoginSuccess: () -> Unit) {
        viewModelScope.launch {
            _state.update { it.copy(_state.value.email, _state.value.senha) }
            try {
                val ok = repo.login(_state.value.email, _state.value.senha)
                if (ok) onLoginSuccess()
                else _state.update { it.copy(loading = false, error = "Falha no login") }
            } catch (e: Exception) {
                _state.update {
                    it.copy(loading = false, error = e.message ?: "Erro no login")
                }
            }
        }
    }
}