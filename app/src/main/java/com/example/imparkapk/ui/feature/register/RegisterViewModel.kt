package com.example.imparkapk.ui.feature.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imparkapk.data.repository.usuarios.DonoRepository
import com.example.imparkapk.domain.model.enuns.TipoDeUsuario
import com.rafaelcosta.modelo_app_crud_usuario_api.data.repository.ClienteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val clienteRepo: ClienteRepository,
    private val donoRepo: DonoRepository
) : ViewModel() {
    private val _state = MutableStateFlow(RegisterUiState())
    val state: MutableStateFlow<RegisterUiState> = _state

    fun onNomeChange(nome: String) {
        _state.value = _state.value.copy(nome = nome)
    }

    fun onEmailChange(email: String) {
        _state.value = _state.value.copy(email = email)
    }

    fun onSenhaChange(senha: String) {
        _state.value = _state.value.copy(senha = senha)
    }

    fun onConfirmarSenhaChange(confirmarSenha: String) {
        _state.value = _state.value.copy(confirmarSenha = confirmarSenha)
    }

    fun onTipoDeUsuarioChange(tipoDeUsuario: String) {
        _state.value = _state.value.copy(tipoDeUsuario = TipoDeUsuario.valueOf(tipoDeUsuario))
    }

    fun onTelefoneChange(telefone: String) {
        _state.value = _state.value.copy(telefone = telefone)
    }

    fun onNascimentoChange(nascimento: Date) {
        _state.value = _state.value.copy(nascimento = nascimento)
    }

    fun registerUser(onRegisterSuccess: () -> Unit) = viewModelScope.launch() {
        val currentState = _state.value
        if (currentState.confirmarSenha == currentState.senha) {
            try {
                when (currentState.tipoDeUsuario) {
                    TipoDeUsuario.CLIENTE -> {
                        clienteRepo.create(
                            nome = currentState.nome,
                            email = currentState.email,
                            senha = currentState.senha,
                            telefone = currentState.telefone,
                            nascimento = currentState.nascimento
                        )
                    }

                    TipoDeUsuario.DONO -> {
                        donoRepo.create(
                            nome = currentState.nome,
                            email = currentState.email,
                            senha = currentState.senha,
                            telefone = currentState.telefone,
                            nascimento = currentState.nascimento
                        )
                    }

                    else -> null
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(errorMessage = e.message)
                return@launch
            }
        }
    }
}