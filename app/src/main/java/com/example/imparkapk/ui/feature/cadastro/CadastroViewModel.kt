package com.example.imparktcc.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imparktcc.repository.CadastroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CadastroViewModel @Inject constructor(
    private val repository: CadastroRepository
): ViewModel (){
    private val _uiState = MutableStateFlow(CadastroUiState())
    val uiState: StateFlow<CadastroUiState> = _uiState.asStateFlow()

    fun  onNomeChange(nome: String){
        _uiState.update { currentState ->
            currentState.copyComValidacao(nome = nome)
        }
    }
    fun onEmailChange(email: String){
        _uiState.update { currentState ->
            currentState.copyComValidacao(email = email)
        }
     }
    fun onSenhaChange(senha: String){
        _uiState.update { currentState ->
            currentState.copyComValidacao(senha = senha)

        }
    }
    fun onConfirmarSenhaChange(confirmarSenha: String) {
        _uiState.update { currentState ->
            currentState.copyComValidacao(confirmarSenha = confirmarSenha)

        }
    }
    fun onCampoFocado(campo: CampoFocado) {
        _uiState.update { currentState ->
            currentState.copyComFoco(campo)
        }
    }
    fun onTermosChange(aceito: Boolean){
        _uiState.update { it.copy(termosAceito = aceito) }
    }
    fun onPoliticaPrivacidadeChange(aceito: Boolean){
        _uiState.update { it.copy(politicaPrivacidadeAceita = aceito) }
    }
    fun onMostrarDicasChange(mostrar: Boolean){
     _uiState.update { it.copy(mostrarDicas = mostrar) }
    }
    fun cadastrarUsuario(onSuccess: () -> Unit = {}){
        if (_uiState.value.botaoCadastroHabilitado){
            _uiState.update { it.copyComLoading() }
            viewModelScope.launch {
                val usuario = _uiState.value.toUsuario()
                val resultado = repository.cadastrarUsuario(usuario)

               resultado.fold(
                   onSuccess = { sucesso ->
                       if (sucesso){
                           _uiState.update { it.copyComSucesso() }
                           onSuccess(usuario)
                       }else{
                           _uiState.update { it.copyComErro("Falha no cadastro. Tente novamente")
                           }
                       }
                   },
                   onFailure ={ erro ->
                       _uiState.copyComErro("Erro: ${erro.message ?: "Tente novamente mais tarde."}")
                   }
               )
            }
        }else{
            _uiState.update { currentState ->
                currentState.copyComValidacao()
            }
        }
    }
    fun limparErros() {
        _uiState.update { it.limparErros() }
    }

    fun resetarEstado() {
        _uiState.update { it.resetarFormulario() }
    }

    fun atualizarEstadoConexao(conectado: Boolean) {
        _uiState.update { it.copyComEstadoConexao(conectado) }
    }
    fun possuiErros(): Boolean{
        return _uiState.value.mensagemErro.isNotEmpty() ||
                !_uiState.value.emailValido ||
                !_uiState.value.senhaValida ||
                !_uiState.value.senhasCoincidem ||
                !_uiState.value.camposPreenchidos
    }
    fun getMetricasCadastro(): Map<String, Any> {
        val state = _uiState.value
        return mapOf(
            "tentativas" to state.tentativasCadastro,
            "tempo_processo" to state.tempoProcessoCadastro,
            "forca_senha" to state.forcaSenha,
            "sucesso" to state.cadastroSucesso,
            "campos_preenchidos" to state.camposPreenchidos
        )
    }
    fun realizarCadastro() {
        val currentState = _uiState.value

        if (!currentState.formularioValido) {
            _uiState.update {
                it.copy(
                    mensagemErro = "Por favor, corrija os erros nos campos acima.",
                    tentativasCadastro = it.tentativasCadastro + 1
                )
            }
            return
        }

        // Inicia o loading
        _uiState.update { it.copyIniciandoCadastro() }

        // Simula a chamada da API
        simulateApiCall { sucesso ->
            _uiState.update { it.copyComResultadoCadastro(sucesso) }
        }
    }

    fun onNavegacaoConcluida() {
        _uiState.update { it.copyComNavegacaoConcluida() }
    }

    // Outras funções...
    fun atualizarNome(nome: String) {
        _uiState.update { it.copyComValidacao(nome = nome) }
    }

    fun atualizarEmail(email: String) {
        _uiState.update { it.copyComValidacao(email = email) }
    }

    fun atualizarSenha(senha: String) {
        _uiState.update { it.copyComValidacao(senha = senha) }
    }

    fun atualizarConfirmarSenha(confirmarSenha: String) {
        _uiState.update { it.copyComValidacao(confirmarSenha = confirmarSenha) }
    }

    private fun simulateApiCall(callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            delay(2000) // Simula delay de rede
            val sucesso = (0..9).random() != 0 // 90% de sucesso
            callback(sucesso)
        }
    }
}