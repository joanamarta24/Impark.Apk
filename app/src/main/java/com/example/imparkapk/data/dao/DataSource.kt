package com.example.imparkapk.data.dao

import com.example.imparkapk.data.dao.repository.UsuarioRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataSource @Inject constructor(
    val usuarioRepository: UsuarioRepository,
    val carroRepository: CarroRepository
) {
}