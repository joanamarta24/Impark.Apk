package com.example.imparkapk.data.dao.remote.api.repository.usuario

import com.example.imparkapk.data.dao.local.dao.dao.ClienteDao
import com.example.imparkapk.data.dao.local.dao.entity.ClienteEntity
import com.example.imparkapk.data.dao.remote.api.api.usuarios.ClienteApi
import com.example.imparkapk.data.dao.remote.api.request.UsuarioRequest
import com.example.imparktcc.model.Usuario
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClienteRepositoryImpl @Inject constructor(
    private val clienteDao: ClienteDao,
    private val clienteApi: ClienteApi
) : ClienteRepository {

    // Cache em memoria para simulação
    private val usuariosCache = mutableListOf<Usuario>()
    private val codigosRecuperacao = mutableMapOf<String, String>() // email -> código
    private var usuarioLogado: Usuario? = null

    init {
        // DADOS DE DEMONSTRAÇÃO
        usuariosCache.addAll(
            listOf(
                Usuario(
                    id = "1",
                    nome = "João Silva",
                    email = "joao.silva@email.com",
                    senha = "Senha123",
                    tipo = "CLIENTE"
                ),
                Usuario(
                    id = "2",
                    nome = "Maria Santos",
                    email = "maria.santos@email.com",
                    senha = "Senha123",
                    tipo = "CLIENTE"
                ),
                Usuario(
                    id = "3",
                    nome = "Carlos Gerente",
                    email = "carlos@estacionamento.com",
                    senha = "Senha123",
                    tipo = "GERENTE"
                )
            )
        )
    }

    override suspend fun buscarUsuarioPorNome(nome: String): List<Usuario> {
        return try {
            delay(500)
            usuariosCache.filter {
                it.nome.contains(nome, ignoreCase = true)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun buscarUsuarioPorEmail(email: String): List<Usuario> {
        return try {
            delay(500)
            usuariosCache.filter {
                it.email.equals(email, ignoreCase = true)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun buscarUsuarioPorTipo(tipo: String): List<Usuario> {
        return try {
            delay(500)
            usuariosCache.filter {
                it.tipo.equals(tipo, ignoreCase = true)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    // MÉTODOS EXISTENTES (mantidos)
    override suspend fun cadastrarUsuario(usuario: Usuario): Result<Boolean> {
        return try {
            delay(2000)

            // Verifica se email já existe
            if (usuariosCache.any { it.email == usuario.email }) {
                return Result.failure(Exception("E-mail já cadastrado"))
            }

            // Simula chamada à API
            val request = UsuarioRequest(
                nome = usuario.nome,
                email = usuario.email,
                senha = usuario.senha
            )

            // Adiciona ao cache (simulação)
            val novoUsuario = usuario.copy(id = UUID.randomUUID().toString())
            usuariosCache.add(novoUsuario)

            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun loginUsuario(email: String, senha: String): Result<Usuario> {
        return try {
            delay(1500)
            val usuario = usuariosCache.find {
                it.email == email && it.senha == senha
            }
            if (usuario != null) {
                usuarioLogado = usuario
                Result.success(usuario)
            } else {
                Result.failure(Exception("E-mail ou senha incorretos"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUsuarioPorId(id: String): Result<Usuario?> {
        return try {
            delay(500)
            val usuario = usuariosCache.find { it.id == id }
            Result.success(usuario)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUsuarioPorEmail(email: String): Result<Usuario?> {
        return try {
            delay(500)
            val usuario = usuariosCache.find { it.email == email }
            Result.success(usuario)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun atualizarUsuario(usuario: Usuario): Result<Boolean> {
        return try {
            delay(1500)
            val index = usuariosCache.indexOfFirst { it.id == usuario.id }
            if (index != -1) {
                usuariosCache[index] = usuario
                Result.success(true)
            } else {
                Result.failure(Exception("Usuário não encontrado"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deletarUsuario(id: String): Result<Boolean> {
        return try {
            delay(1000)
            val removed = usuariosCache.removeAll { it.id == id }
            Result.success(removed)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun recuperarSenha(email: String): Boolean {
        return try {
            delay(2000)
            if (usuariosCache.any { it.email == email }) {
                // Gera código de 6 dígitos
                val codigo = (100000..999999).random().toString()
                codigosRecuperacao[email] = codigo
                println("Código de recuperação para $email: $codigo")
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun verificarCodigoRecuperacao(email: String, codigo: String): Boolean {
        return try {
            delay(1500)
            val codigoSalvo = codigosRecuperacao[email]
            codigoSalvo == codigo
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun redefinirSenha(email: String, codigo: String, novaSenha: String): Boolean {
        return try {
            if (!verificarCodigoRecuperacao(email, codigo)) {
                return false
            }
            // Atualizar senha
            val usuario = usuariosCache.find { it.email == email }
            if (usuario != null) {
                val index = usuariosCache.indexOf(usuario)
                usuariosCache[index] = usuario.copy(senha = novaSenha)

                // Remove código usado
                codigosRecuperacao.remove(email)

                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    override fun validarEmail(email: String): Boolean {
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$")
        return emailRegex.matches(email) && email.isNotEmpty()
    }

    override fun validarSenha(senha: String): Boolean {
        return senha.length >= 6 &&
                senha.any { it.isDigit() } &&
                senha.any { it.isLetter() } &&
                senha.isNotBlank()
    }

    override fun getUsuariosAtivos(): Flow<List<Usuario>> {
        return flow {
            emit(usuariosCache)
        }
    }

    override suspend fun countUsuarios(): Result<Int> {
        return try {
            Result.success(usuariosCache.size)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun buscarUsuariosPorNome(nome: String): Result<List<Usuario>> {
        return try {
            val usuarios = usuariosCache.filter {
                it.nome.contains(nome, ignoreCase = true)
            }
            Result.success(usuarios)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun salvarSessaoUsuario(usuario: Usuario): Result<Boolean> {
        return try {
            usuarioLogado = usuario
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun recuperacaoSessaoUsuarios(): Result<Usuario?> {
        return try {
            Result.success(usuarioLogado)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun limparSessaoUsuario(): Result<Boolean> {
        return try {
            usuarioLogado = null
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun bloquearUsuario(id: String): Result<Boolean> {
        return try {
            // Implementação de bloqueio
            val usuario = usuariosCache.find { it.id == id }
            if (usuario != null) {
                val index = usuariosCache.indexOf(usuario)
                usuariosCache[index] = usuario.copy(ativo = false) // Supondo que tenha campo ativo
                Result.success(true)
            } else {
                Result.success(false)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun desbloquearUsuario(id: String): Result<Boolean> {
        return try {
            // Implementação de desbloqueio
            val usuario = usuariosCache.find { it.id == id }
            if (usuario != null) {
                val index = usuariosCache.indexOf(usuario)
                usuariosCache[index] = usuario.copy(ativo = true) // Supondo que tenha campo ativo
                Result.success(true)
            } else {
                Result.success(false)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun atualizarUtimoAcesso(id: String): Result<Boolean> {
        return try {
            // Implementação de atualização de último acesso
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

// Extension function para conversão (se estivesse usando Room)
private fun Usuario.toEntity(): ClienteEntity {
    return ClienteEntity(
        id = this.id,
        nome = this.nome,
        email = this.email,
        senha = this.senha,
        dataCriacao = Date(),
        dataAtualizacao = Date(),
        ativo = true
    )
}