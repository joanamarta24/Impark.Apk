package com.example.imparkapk.ui.feature.login

import androidx.lifecycle.ViewModel
import com.example.imparktcc.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: AuthRepository
) : ViewModel() {

    val _state = MutableStateFlow(LoginUiState())
}