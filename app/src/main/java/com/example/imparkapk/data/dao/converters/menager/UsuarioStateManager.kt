package com.example.imparkapk.data.dao.converters.menager

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.imparkapk.UiState.UsuarioUiState
import javax.inject.Inject

class UsuarioStateManager @Inject constructor(
    private val _uiSate = mutableStateOf(usuarioUiState())
    val uiState: State<UsuarioUiState>
) {
}