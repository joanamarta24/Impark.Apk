package com.example.imparkapk.data.dao.ui.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CadastroGerenteViewlModel  @Inject constructor(
    private val cadastrarGerenteUsecase:CadastrarGerenteUseCase
): ViewModel(){
    private val _uiState = MutableStateFlow(CadastroGerenteUiState())

}