package com.example.imparkapk.data.dao.remote.api.repository.usuario

import com.example.imparkapk.data.dao.model.enus.Cliente
import kotlinx.coroutines.flow.Flow

interface ClienteRepository {

    suspend fun buscarUsuarioPorNome(nome: String): List<Cliente>
    suspend fun buscarUsuarioPorEmail(email: String): List<Cliente>
    suspend fun buscarUsuarioPorTipo(tipo: String): List<Cliente>

    // Operações básicas de usuário
    suspend fun cadastrarCliente(usuario: Cliente): Result<Boolean>
    suspend fun loginUsuario(email: String, senha: String): Result<Cliente>
    suspend fun getUsuarioPorId(id: String): Result<Cliente?>
    suspend fun getUsuarioPorEmail(email: String): Result<Cliente?>
    suspend fun atualizarUsuario(usuario:Cliente): Result<Boolean>
    suspend fun deletarUsuario(id: String): Result<Boolean>

    // Recuperação de senha
    suspend fun recuperarSenha(email: String):Boolean
    suspend fun verificarCodigoRecuperacao(email: String,codigo:String):Boolean
    suspend fun redefinirSenha(email: String,codigo: String,novaSenha:String):Boolean

    // Validações
    fun validarEmail(email: String):Boolean
    fun validarSenha(senha: String):Boolean

    // Operações em lote
    fun getUsuariosAtivos(): Flow<List<Cliente>>
    suspend fun countUsuarios(): Result<Int>
    suspend fun buscarUsuariosPorNome(nome:String):Result<List<Cliente>>

    // Gerenciamento de sessão
    suspend fun salvarSessaoCliente(usuario: Cliente):Result<Boolean>
    suspend fun recuperacaoSessaoCliente():Result<Cliente?>
    suspend fun limparSessaoUsuario():Result<Boolean>

    // Operações administrativas
    suspend fun bloquearUsuario(id:String):Result<Boolean>
    suspend fun desbloquearUsuario(id: String):Result<Boolean>
    suspend fun atualizarUtimoAcesso(id: String):Result<Boolean>
    fun existeUsuario(usuarioId: String?)
}