package com.example.imparkapk.ui.theme.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedState
import com.example.imparkapk.repository.CadastroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.security.Key

@HiltViewModel
class CadastroViewModel @inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: CadastroRepository
):ViewModel(){
    companion object{
        private const val KEY_NOME = "nome"
        private const val KEY_EMAIL = "email"
        private const val KEY_SENHA = "senha"
        private const val KEY_CONFIRMAR_SENHA = "confirmar_senha"
        private const val KEY_TERMOS_ACEITOS = "termos_aceitos"
        private const val KEY_POLITICA_ACEITA = "politica_aceita"
        private const val KEY_MOSTRAR_DICAS = "mostrar_dicas"
        private const val KEY_CAMPO_FOCO = "campo_foco"
        private const val KEY_CAMPOS_VISITADOS = "campos_visitados"
        private const val KEY_TENTATIVAS = "tentativas_cadastro"

    }
    private val _uiState = MutableStateFlow(createInitialState())
}