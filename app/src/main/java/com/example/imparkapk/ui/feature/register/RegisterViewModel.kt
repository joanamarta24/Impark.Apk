package com.example.imparkapk.ui.feature.register

import androidx.lifecycle.ViewModel
import com.example.imparktcc.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repo: AuthRepository
) : ViewModel() {
}