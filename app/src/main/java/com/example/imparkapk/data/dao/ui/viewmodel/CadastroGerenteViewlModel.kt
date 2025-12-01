package com.example.imparkapk.data.dao.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.imparkapk.data.dao.local.dao.TokenStore
import com.example.imparkapk.data.dao.remote.api.repository.gerente.CadastrarGerenteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CadastroGerenteViewlModel  @Inject constructor(
    private val cadastroGerenteUsecase: CadastrarGerenteUseCase,
    private val tokenStore: TokenStore
): ViewModel(){
    private val _uiState = MutableStateFlow(CadastroGerenteUiState())
    val uiState: StateFlow<CadastroGerenteUiState>

}