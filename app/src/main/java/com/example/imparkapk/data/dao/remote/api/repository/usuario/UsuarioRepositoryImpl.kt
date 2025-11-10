package com.example.imparkapk.data.dao.remote.api.repository.usuario

import com.example.imparkapk.data.UsuarioDao
import com.example.imparkapk.data.dao.entity.UsuarioEntity
import com.example.imparkapk.data.dao.remote.api.api.UsuarioApi
import com.example.imparkapk.data.dao.remote.api.request.UsuarioRequest
import com.example.imparktcc.model.Usuario
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import java.util.Date
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsuarioRepositoryImpl @Inject constructor(
    private val usuarioDao: UsuarioDao,
    private val usuarioApi: UsuarioApi
): UsuarioRepository {
    //Cache em memoria para simulacão
    private val usuariosCache = mutableListOf<Usuario>()
    private val codigosRecuperacao = mutableMapOf<String, String>() // email -> código
    private var usuarioLogado: Usuario? = null

    init {
        //DADOS DE DEMONSTRAÇÃO
        usuariosCache.addAll(
            listOf(
                Usuario(
                    id = "1",
                    nome = "João Silva",
                    email = "joao.silva@email.com",
                    senha = "Senha123"
                ),
                Usuario(
                    id = "2",
                    nome = "Maria Santos",
                    email = "maria.santos@email.com",
                    senha = "Senha123"
                )
            )
        )
    }


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
                Result.failure(Exception("E-mail ou senha incorretps"))
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
            val index = usuariosCache.indexOfLast { it.id == usuario.id }
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
            //Atualizar senha
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
        return kontlinx.coroutines.flow.flow {
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
            // Implementação de desbloqueio
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun desbloquearUsuario(id: String): Result<Boolean> {
        return try {
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun atualizarUtimoAcesso(id: String): Result<Boolean> {
        return try {
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
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
    }

        // Extension function para conversão (se estivesse usando Room)

        private fun Usuario.toEntity(): UsuarioEntity {
            return UsuarioEntity(
                id = this.id,
                nome = this.nome,
                email = this.email,
                senha = this.senha,
                dataCriacao = Date(),
                dataAtualizacao = Date(),
                ativo = true
            )
        }
    }