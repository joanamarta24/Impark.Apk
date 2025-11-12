package com.example.imparkapk.data.dao.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.imparkapk.data.dao.converters.menager.UsuarioStateManager
import com.example.imparkapk.data.dao.remote.api.repository.usuario.UsuarioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UsuarioViewModel @Inject constructor(

) : ViewModel () {
    private val usuarioRepository: UsuarioRepository,
    private val stateManager: UsuarioStateManager
}