package com.example.imparkapk.ui.feature.dashboard

import androidx.lifecycle.ViewModel
import com.rafaelcosta.modelo_app_crud_usuario_api.data.repository.EstacionamentoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repo: EstacionamentoRepository
) : ViewModel(){
    private val _state = MutableStateFlow(DashboardUiState())
    val state: StateFlow<DashboardUiState> = _state
}
