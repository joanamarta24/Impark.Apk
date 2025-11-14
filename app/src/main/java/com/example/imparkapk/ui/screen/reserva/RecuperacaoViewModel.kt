package com.example.imparkapk.ui.screen.reserva

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imparkapk.data.local.remote.api.repository.usuario.UsuarioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecuperacaoViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecuperacaoUiState())
    val uiState: StateFlow<RecuperacaoUiState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, emailValido = true) }
    }

    fun onCodigoChange(codigo: String) {
        _uiState.update { it.copy(codigo = codigo, codigoValido = true) }
    }

    fun onNovaSenhaChange(novaSenha: String) {
        _uiState.update { it.copy(
            novaSenha = novaSenha,
            senhaValida = true,
            senhasCoincidem = true
        ) }
    }

    fun onConfirmarSenhaChange(confirmarSenha: String) {
        _uiState.update { it.copy(
            confirmarSenha = confirmarSenha,
            senhasCoincidem = true
        ) }
    }

    fun solicitarCodigoRecuperacao(onSuccess: (String) -> Unit = {}) {
        if (validarEmail()) {
            _uiState.update { it.copy(isLoading = true, mensagemErro = "") }

            viewModelScope.launch {
                try {
                    val sucesso = usuarioRepository.recuperarSenha(_uiState.value.email)

                    if (sucesso) {
                        _uiState.update { it.copy(
                            isLoading = false,
                            codigoEnviado = true,
                            mensagemSucesso = "Código enviado para seu e-mail!"
                        ) }
                        onSuccess(_uiState.value.email)
                    } else {
                        _uiState.update { it.copy(
                            isLoading = false,
                            mensagemErro = "Erro ao enviar código. Tente novamente."
                        ) }
                    }
                } catch (e: Exception) {
                    _uiState.update { it.copy(
                        isLoading = false,
                        mensagemErro = "Erro de conexão. Verifique sua internet."
                    ) }
                }
            }
        }
    }

    fun verificarCodigo(onSuccess: (String, String) -> Unit = { _, _ -> }) {
        if (validarCodigo()) {
            _uiState.update { it.copy(isLoading = true, mensagemErro = "") }

            viewModelScope.launch {
                // Simulação de verificação de código
                delay(1500)

                val codigoValido = _uiState.value.codigo == "123456" // Código fixo para demonstração

                if (codigoValido) {
                    _uiState.update { it.copy(
                        isLoading = false,
                        codigoVerificado = true,
                        mensagemSucesso = "Código verificado com sucesso!"
                    ) }
                    onSuccess(_uiState.value.email, _uiState.value.codigo)
                } else {
                    _uiState.update { it.copy(
                        isLoading = false,
                        mensagemErro = "Código inválido. Tente novamente."
                    ) }
                }
            }
        }
    }

    fun redefinirSenha(onSuccess: () -> Unit = {}) {
        if (validarSenhas()) {
            _uiState.update { it.copy(isLoading = true, mensagemErro = "") }

            viewModelScope.launch {
                try {
                    // Aqui você implementaria a chamada real para redefinir a senha
                    val sucesso = true // Simulação

                    if (sucesso) {
                        _uiState.update { it.copy(
                            isLoading = false,
                            senhaRedefinida = true,
                            mensagemSucesso = "Senha redefinida com sucesso!"
                        ) }
                        onSuccess()
                    } else {
                        _uiState.update { it.copy(
                            isLoading = false,
                            mensagemErro = "Erro ao redefinir senha. Tente novamente."
                        ) }
                    }
                } catch (e: Exception) {
                    _uiState.update { it.copy(
                        isLoading = false,
                        mensagemErro = "Erro de conexão. Verifique sua internet."
                    ) }
                }
            }
        }
    }

    private fun validarEmail(): Boolean {
        val emailValido = usuarioRepository.validarEmail(_uiState.value.email)
        _uiState.update { it.copy(emailValido = emailValido) }
        return emailValido
    }

    private fun validarCodigo(): Boolean {
        val codigoValido = _uiState.value.codigo.length == 6 && _uiState.value.codigo.all { it.isDigit() }
        _uiState.update { it.copy(codigoValido = codigoValido) }
        return codigoValido
    }

    private fun validarSenhas(): Boolean {
        val senhaValida = usuarioRepository.validarSenha(_uiState.value.novaSenha)
        val senhasCoincidem = _uiState.value.novaSenha == _uiState.value.confirmarSenha

        _uiState.update { it.copy(
            senhaValida = senhaValida,
            senhasCoincidem = senhasCoincidem
        ) }

        return senhaValida && senhasCoincidem
    }

    fun limparMensagens() {
        _uiState.update { it.copy(mensagemErro = "", mensagemSucesso = "") }
    }

    fun resetState() {
        _uiState.value = RecuperacaoUiState()
    }
}

data class RecuperacaoUiState(
    val email: String = "",
    val codigo: String = "",
    val novaSenha: String = "",
    val confirmarSenha: String = "",
    val emailValido: Boolean = true,
    val codigoValido: Boolean = true,
    val senhaValida: Boolean = true,
    val senhasCoincidem: Boolean = true,
    val isLoading: Boolean = false,
    val codigoEnviado: Boolean = false,
    val codigoVerificado: Boolean = false,
    val senhaRedefinida: Boolean = false,
    val mensagemErro: String = "",
    val mensagemSucesso: String = ""
) {
    val botaoSolicitarHabilitado: Boolean get() = email.isNotBlank() && !isLoading
    val botaoVerificarHabilitado: Boolean get() = codigo.length == 6 && !isLoading
    val botaoRedefinirHabilitado: Boolean get() = novaSenha.isNotBlank() && confirmarSenha.isNotBlank() && !isLoading
}