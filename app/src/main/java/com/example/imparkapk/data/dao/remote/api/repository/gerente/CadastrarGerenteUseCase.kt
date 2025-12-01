package com.example.imparkapk.data.dao.remote.api.repository.gerente

import com.example.imparkapk.data.dao.model.Gerente
import com.example.imparkapk.data.dao.remote.api.repository.usuario.ClienteRepository
import javax.inject.Inject

class CadastrarGerenteUseCase @Inject constructor(
    private val gerenteRepository: GerenteRepository,
    private val clienteRepository: ClienteRepository
) {
    suspend operator fun invoke(gerente: Gerente, usuarioLogadoId: String): Result<Gerente> {
        return try {
            // 1. Validações básicas
            val validationResult = validateGerente(gerente)
            if (!validationResult.isValid) {
                return Result.failure(Exception(validationResult.errorMessage))
            }

            // 2. Verificar permissão do usuário logado
            val usuarioLogadoTemPermissao = verificarPermissaoUsuarioLogado(
                usuarioLogadoId,
                gerente.estacionamentoId
            )

            if (!usuarioLogadoTemPermissao) {
                return Result.failure(Exception("Usuário não tem permissão para cadastrar gerentes"))
            }

            // 3. Verificar se o usuário existe
            val usuarioExiste = clienteRepository.existeUsuario(gerente.usuarioId)
            if (!usuarioExiste) {
                return Result.failure(Exception("Usuário não encontrado"))
            }

            // 4. Verificar se o usuário já é gerente
            val gerenteExistente = gerenteRepository.getGerentePorUsuarioEEstacionamento(
                usuarioId = gerente.usuarioId,
                estacionamentoId = gerente.estacionamentoId
            )

            if (gerenteExistente.isSuccess && gerenteExistente.getOrNull() != null) {
                val gerenteAtual = gerenteExistente.getOrNull()!!
                return if (gerenteAtual.ativo) {
                    Result.failure(Exception("Usuário já é gerente ativo neste estacionamento"))
                } else {
                    // Se está inativo, reativar
                    val reativado = gerenteRepository.ativarGerente(gerenteAtual.id)
                    if (reativado) {
                        Result.success(gerenteAtual.copy(ativo = true))
                    } else {
                        Result.failure(Exception("Erro ao reativar gerente"))
                    }
                }
            }

            // 5. Verificar limite de gerentes
            val limiteAtingido = verificarLimiteGerentes(gerente.estacionamentoId)
            if (limiteAtingido) {
                return Result.failure(Exception("Limite máximo de gerentes atingido para este estacionamento"))
            }

            // 6. Verificar gerente principal
            if (gerente.nivelAcesso == Gerente.Companion.NIVEL_GERENTE) {
                val jaTemGerentePrincipal = gerenteRepository.listarGerentesPorEstacionamento(
                    gerente.estacionamentoId
                ).any { it.nivelAcesso == Gerente.Companion.NIVEL_GERENTE && it.ativo }

                if (jaTemGerentePrincipal) {
                    return Result.failure(Exception("Já existe um gerente principal neste estacionamento"))
                }
            }

            // 7. Cadastrar gerente
            val sucesso = gerenteRepository.cadastrarGerente(gerente)

            if (sucesso) {
                // Buscar gerente recém-cadastrado
                val novoGerente = gerenteRepository.getGerentePorUsuarioEEstacionamento(
                    usuarioId = gerente.usuarioId,
                    estacionamentoId = gerente.estacionamentoId
                )

                novoGerente.getOrNull()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Gerente cadastrado mas não encontrado"))
            } else {
                Result.failure(Exception("Falha ao cadastrar gerente"))
            }

        } catch (e: Exception) {
            Result.failure(Exception("Erro ao cadastrar gerente: ${e.message}"))
        }
    }

    private suspend fun verificarPermissaoUsuarioLogado(
        usuarioLogadoId: String,
        estacionamentoId: String
    ): Boolean {
        return try {
            // Verificar se usuário logado é dono ou gerente principal
            val gerenteLogado = gerenteRepository.getGerentePorUsuarioEEstacionamento(
                usuarioId = usuarioLogadoId,
                estacionamentoId = estacionamentoId
            )

            gerenteLogado.getOrNull()?.let { gerente ->
                gerente.ativo && (gerente.nivelAcesso == Gerente.Companion.NIVEL_DONO ||
                        gerente.nivelAcesso == Gerente.Companion.NIVEL_GERENTE)
            } ?: false
        } catch (e: Exception) {
            false
        }
    }

    private suspend fun verificarLimiteGerentes(estacionamentoId: String): Boolean {
        val totalGerentes = gerenteRepository.countGerentesPorEstacionamento(estacionamentoId)
        // Limite máximo de 5 gerentes por estacionamento
        return totalGerentes >= 5
    }

    private fun validateGerente(gerente: Gerente): ValidationResult {
        val errors = mutableListOf<String>()

        if (gerente.usuarioId.isBlank()) errors.add("ID do usuário é obrigatório")
        if (gerente.estacionamentoId.isBlank()) errors.add("ID do estacionamento é obrigatório")
        if (gerente.nome.isBlank()) errors.add("Nome é obrigatório")
        if (gerente.email.isBlank()) errors.add("Email é obrigatório")
        if (gerente.cpf.isBlank()) errors.add("CPF é obrigatório")
        if (gerente.telefone.isBlank()) errors.add("Telefone é obrigatório")

        if (gerente.email.isNotBlank() && !isValidEmail(gerente.email)) {
            errors.add("Email inválido")
        }

        if (gerente.cpf.isNotBlank() && !isValidCpf(gerente.cpf)) {
            errors.add("CPF inválido")
        }

        val niveisValidos = listOf(
            Gerente.Companion.NIVEL_GERENTE,
            Gerente.Companion.NIVEL_SUPERVISOR,
            Gerente.Companion.NIVEL_FUNCIONARIO
        )
        if (gerente.nivelAcesso !in niveisValidos) {
            errors.add("Nível de acesso inválido. Valores permitidos: ${niveisValidos.joinToString()}")
        }

        return ValidationResult(
            isValid = errors.isEmpty(),
            errors = errors
        )
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$")
        return emailRegex.matches(email)
    }

    private fun isValidCpf(cpf: String): Boolean {
        val cleanCpf = cpf.replace(Regex("[^0-9]"), "")
        if (cleanCpf.length != 11) return false

        // Validação de dígitos verificadores
        val digits = cleanCpf.map { it.toString().toInt() }

        // Primeiro dígito verificador
        var sum = 0
        for (i in 0..8) {
            sum += digits[i] * (10 - i)
        }
        var firstDigit = 11 - (sum % 11)
        if (firstDigit >= 10) firstDigit = 0

        // Segundo dígito verificador
        sum = 0
        for (i in 0..9) {
            sum += digits[i] * (11 - i)
        }
        var secondDigit = 11 - (sum % 11)
        if (secondDigit >= 10) secondDigit = 0

        return digits[9] == firstDigit && digits[10] == secondDigit
    }

    data class ValidationResult(
        val isValid: Boolean,
        val errors: List<String>
    ) {
        val errorMessage: String
            get() = errors.joinToString(", ")
    }
}