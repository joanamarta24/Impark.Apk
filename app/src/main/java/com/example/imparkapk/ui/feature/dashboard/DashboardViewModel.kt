package com.example.imparkapk.ui.feature.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imparkapk.domain.model.Estacionamento
import com.example.imparkapk.domain.model.enuns.TipoDeUsuario
import com.rafaelcosta.modelo_app_crud_usuario_api.data.repository.EstacionamentoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repo: EstacionamentoRepository
) : ViewModel(){
    private val _state = MutableStateFlow(DashboardUiState())
    val state: StateFlow<DashboardUiState> = _state

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

    fun onNascimentoChange(nascimento: Date) {
        _state.value = _state.value.copy(nascimento = nascimento)
    }

    fun onPesquisarChange(pesquisa: String) {
        _state.value = _state.value.copy(pesquisa = pesquisa)
    }

    fun onEstacionamentosChange(estacionamentos: List<Estacionamento>) {
        _state.value = _state.value.copy(estacionamentos = estacionamentos)
    }

    fun loadEstacio() {
        viewModelScope.launch {
            try {
                repo.syncAll()
                repo.observeEstacionamentos().collect { estacioList -> {
                    onEstacionamentosChange(estacionamentos = estacioList)
                    }
                }
            } catch (e: Exception) {
                Log.e("Erro de sincronização", e.message?:"Erro desconhecido")
            }
        }
    }
}
