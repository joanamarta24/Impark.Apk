package com.example.imparkapk.data.dao.remote.api.repository

import com.example.imparkapk.data.dao.remote.api.api.UsuarioApi
import com.example.imparkapk.data.dao.dao.UsuarioDao
import com.example.imparktcc.model.Usuario
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsuarioRepositoryImpl @Inject constructor(
    private val usuarioDao: UsuarioDao,
    private val usuarioApi: UsuarioApi
):UsuarioRepository {
    //Cache em memoria para simulacão
    private val usuariosCache = mutableListOf<Usuario>()
    private val codigosRecuperacao = mutableMapOf<String, String>() // email -> código
    private var usuarioLogado: Usuario? = null
}