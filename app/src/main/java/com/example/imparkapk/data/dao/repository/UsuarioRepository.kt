package com.example.imparkapk.data.dao.repository

import com.example.imparktcc.model.Usuario
import kotlinx.coroutines.flow.Flow

interface UsuarioRepository {

    suspend fun cadastrarUsuario(usuario: Usuario): Result<Boolean>

    suspend fun  getUsuarioByEmail(email: String): Result<Usuario?>

    suspend fun getUsuatioPorEmail(email: String): Result<Usuario?>

    suspend fun atualizarUsuario(usuario: Usuario): Result<Boolean>

    suspend fun deletarUsuario(usuario: Usuario): Result<Boolean>

    fun getUsuariosAtivos(): Flow<List<Usuario>>

    suspend fun validarEmail(email: String): Boolean

    suspend fun validarSenha(senha: String): Boolean
}


