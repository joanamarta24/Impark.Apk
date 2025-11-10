package com.example.imparkapk.data.dao.remote.api.repository

import com.example.imparktcc.model.Usuario
import kotlinx.coroutines.flow.Flow

interface UsuarioRepository {

    suspend fun buscarUsuarioPorNome(nome: String): List<Usuario>
    suspend fun buscarUsuarioPorEmail(email: String): List<Usuario>
    suspend fun buscarUsuarioPorTipo(tipo: String): List<Usuario>

    // Operações básicas de usuário
    suspend fun cadastrarUsuario(usuario: Usuario): Result<Boolean>
    suspend fun loginUsuario(email: String, senha: String): Result<Usuario>
    suspend fun getUsuarioPorId(id: String): Result<Usuario?>
    suspend fun getUsuarioPorEmail(email: String): Result<Usuario?>
    suspend fun atualizarUsuario(usuario: Usuario): Result<Boolean>
    suspend fun deletarUsuario(id: String): Result<Boolean>

    // Recuperação de senha
    suspend fun recuperarSenha(email: String):Boolean
    suspend fun verificarCodigoRecuperacao(email: String,codigo:String):Boolean
    suspend fun redefinirSenha(email: String,codigo: String,novaSenha:String):Boolean

    // Validações
    fun validarEmail(email: String):Boolean
    fun validarSenha(senha: String):Boolean

    // Operações em lote
    fun getUsuariosAtivos():Flow<List<Usuario>>
    suspend fun countUsuarios(): Result<Int>
    suspend fun buscarUsuariosPorNome(nome:String):Result<List<Usuario>>

    // Gerenciamento de sessão
    suspend fun salvarSessaoUsuario(usuario: Usuario):Result<Boolean>
    suspend fun recuperacaoSessaoUsuarios():Result<Usuario?>
    suspend fun limparSessaoUsuario():Result<Boolean>

    // Operações administrativas
    suspend fun bloquearUsuario(id:String):Result<Boolean>
    suspend fun desbloquearUsuario(id: String):Result<Boolean>
    suspend fun atualizarUtimoAcesso(id: String):Result<Boolean>
}


