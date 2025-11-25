package com.example.imparkapk.ui.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imparktcc.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface AuthState {
    data object Loading : AuthState
    data object Unauthenticated : AuthState
    data class Authenticated(val meCachedNome: String?, val meCachedEmail: String?) : AuthState
}

@HiltViewModel
class AuthStateViewModel @Inject constructor(
    private val repo: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow<AuthState>(AuthState.Loading)
    val state: MutableStateFlow<AuthState> = _state

    init {
        viewModelScope.launch {
            val me = repo.bootstrapSession()
            if (me != null) {
                _state.value = AuthState.Authenticated(me.nome, me.email)
            } else {
                _state.value = AuthState.Unauthenticated
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repo.logout()
            _state.value = AuthState.Unauthenticated
        }
    }
}