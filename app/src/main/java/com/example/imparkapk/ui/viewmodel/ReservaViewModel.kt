package com.example.imparkapk.ui.viewmodel

import com.example.imparkapk.data.dao.remote.api.repository.ReservaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReservaViewModel @Inject constructor(
    private val reservaRepository: ReservaRepository,
    private val carroRepository:CarroRepository
){
}