package com.example.imparktcc.ui.viewmodel

import com.example.imparktcc.model.Usuario


enum class CampoFocado {
    NOME, EMAIL, SENHA, CONFIRMAR_SENHA, TERMOS, POLITICA, NONE
}
data class CadastroUiState(
    // Campos do formulário
    val nome: String = "",
    val email: String = "",
    val senha: String = "",
    val confirmarSenha: String = "",

    // Estados de validação
    val emailValido: Boolean = true,
    val senhaValida: Boolean = true,
    val senhasCoincidem: Boolean = true,
    val camposPreenchidos: Boolean = true,

    // Estados de UI
    val cadastroSucesso: Boolean = false,
    val isLoading: Boolean = false,
    val mensagemErro: String = "",

    // Estados avançados
    val campoComFoco: CampoFocado? = null,
    val tentativasCadastro: Int = 0,
    val mostrarDicas: Boolean = false,
    val termosAceito: Boolean = false,
    val politicaPrivacidadeAceita: Boolean = false,

    // Timestamps para analytics
    val timestampInicioCadastro: Long = 0L,
    val timestampFimCadastro: Long = 0L,

    // Estados de rede
    val conexaoInternet: Boolean = true,
    val servidorOnLine: Boolean = true,

    // Campos adicionais para tracking
    val camposVisitados: Set<CampoFocado> = emptySet()
) {

    // Verifica se o formulário está completamente válido
    val formularioValido: Boolean
        get() = nome.isNotBlank() &&
                email.isNotBlank() &&
                senha.isNotBlank() &&
                confirmarSenha.isNotBlank() &&
                emailValido &&
                senhaValida &&
                senhasCoincidem &&
                termosAceito &&
                politicaPrivacidadeAceita

    // Verifica se pode habilitar o botão de cadastro
    val botaoCadastroHabilitado: Boolean
        get() = formularioValido && !isLoading && conexaoInternet

    // Calcula a força da senha (0-4)
    val forcaSenha: Int
        get() = calculatePasswordStrength(senha)

    val tempoProcessoCadastro: Long
        get() = if (timestampFimCadastro > timestampInicioCadastro) {
            (timestampFimCadastro - timestampInicioCadastro) / 1000
        } else 0L

    val exibirAvisoTentativas: Boolean
        get() = tentativasCadastro >= 3 && !cadastroSucesso

    val progressoCadastro: Int
        get() = when {
            cadastroSucesso -> 100
            isLoading -> 50
            formularioValido -> 25
            else -> 0
        }

    fun copyComValidacao(
        nome: String = this.nome,
        email: String = this.email,
        senha: String = this.senha,
        confirmarSenha: String = this.confirmarSenha
    ): CadastroUiState {
        return this.copy(
            nome = nome,
            email = email,
            senha = senha,
            confirmarSenha = confirmarSenha,
            // Revalida todos os campos
            camposPreenchidos = nome.isNotBlank() && email.isNotBlank() &&
                    senha.isNotBlank() && confirmarSenha.isNotBlank(),
            emailValido = isValidEmail(email),
            senhaValida = isValidPassword(senha),
            senhasCoincidem = senha == confirmarSenha && senha.isNotBlank()
        )
    }

    fun copyComLoading(): CadastroUiState {
        return this.copy(
            isLoading = true,
            mensagemErro = "",
            timestampInicioCadastro = System.currentTimeMillis()
        )
    }

    fun copyComSucesso(): CadastroUiState {
        return this.copy(
            isLoading = false,
            cadastroSucesso = true,
            mensagemErro = "",
            timestampFimCadastro = System.currentTimeMillis()
        )
    }

    fun copyComFoco(campo: CampoFocado): CadastroUiState {
        return this.copy(
            campoComFoco = campo,
            camposVisitados = camposVisitados + campo
        )
    }

    fun copyComEstadoConexao(conectado: Boolean): CadastroUiState {
        return this.copy(conexaoInternet = conectado)
    }

    fun getMensagemErroCampo(campo: CampoFocado? = null): String? {
        val campoAlvo = campo ?: campoComFoco

        return when (campoAlvo) {
            CampoFocado.NOME -> when {
                nome.isBlank() -> "Nome é obrigatório"
                nome.length < 2 -> "Nome deve ter pelo menos 2 caracteres"
                nome.length > 100 -> "Nome muito longo"
                !nome.contains(" ") -> "Digite nome e sobrenome"
                else -> null
            }

            CampoFocado.EMAIL -> when {
                email.isBlank() -> "E-mail é obrigatório"
                !emailValido -> "Digite um e-mail válido (exemplo@email.com)"
                email.length > 150 -> "E-mail muito longo"
                else -> null
            }

            CampoFocado.SENHA -> when {
                senha.isBlank() -> "Senha é obrigatória"
                !senhaValida -> "Senha deve ter 6+ caracteres com letras e números"
                senha.length > 50 -> "Senha muito longa"
                forcaSenha <= 1 -> "Senha muito fraca. Use letras maiúsculas, minúsculas e números"
                else -> null
            }

            CampoFocado.CONFIRMAR_SENHA -> when {
                confirmarSenha.isBlank() -> "Confirme sua senha"
                !senhasCoincidem -> "As senhas não coincidem"
                else -> null
            }

            CampoFocado.TERMOS -> when {
                !termosAceito -> "Você deve aceitar os termos e condições"
                else -> null
            }

            CampoFocado.POLITICA -> when {
                !politicaPrivacidadeAceita -> "Você deve aceitar a política de privacidade"
                else -> null
            }

            CampoFocado.NONE -> null
            null -> null
        }
    }

    fun getDicaCampo(campo: CampoFocado): String {
        return when (campo) {
            CampoFocado.NOME -> "Use seu nome completo (nome e sobrenome)"
            CampoFocado.EMAIL -> "exemplo@email.com"
            CampoFocado.SENHA -> when (forcaSenha) {
                0 -> "Digite uma senha segura"
                1 -> "Senha muito fraca - adicione números e letras"
                2 -> "Senha fraca - use letras maiúsculas e minúsculas"
                3 -> "Senha boa - considere adicionar caracteres especiais"
                4 -> "Senha forte - excelente!"
                else -> "Digite uma senha"
            }

            CampoFocado.CONFIRMAR_SENHA -> "Digite a mesma senha para confirmação"
            CampoFocado.TERMOS -> "Leia e aceite os termos para continuar"
            CampoFocado.POLITICA -> "Leia e aceite a política de privacidade"
            CampoFocado.NONE -> ""
        }
    }

    fun getPlaceholderCampo(campo: CampoFocado): String {
        return when (campo) {
            CampoFocado.NOME -> "Cristian Silva"
            CampoFocado.EMAIL -> "seu.email@email.com"
            CampoFocado.SENHA -> "Digite sua senha"
            CampoFocado.CONFIRMAR_SENHA -> "Digite a senha novamente"
            else -> ""
        }
    }

    fun toUsuario(): Usuario {
        return Usuario(
            nome = nome.trim(),
            email = email.trim().lowercase(),
            senha = senha.trim()
        )
    }

    fun resetarFormulario(): CadastroUiState {
        return CadastroUiState(
            mostrarDicas = this.mostrarDicas,
            conexaoInternet = this.conexaoInternet,
            servidorOnLine = this.servidorOnLine,
        )
    }

    fun limparErros(): CadastroUiState {
        return this.copy(
            mensagemErro = "",
            emailValido = true,
            senhaValida = true,
            senhasCoincidem = true,
            camposPreenchidos = true,
        )
    }

    fun getMetricasUso(): Map<String, Any> {
        return mapOf(
            "campos_visitados" to camposVisitados.size,
            "tempo_foco_total" to tempoProcessoCadastro,
            "tentativas" to tentativasCadastro,
            "forca_senha_final" to forcaSenha,
            "sucesso" to cadastroSucesso
        )
    }

    fun deveSugerirAutoPreenchimento(): Boolean {
        return camposVisitados.size >= 2 &&
                !formularioValido &&
                tentativasCadastro == 0
    }

    companion object {
        private fun isValidEmail(email: String): Boolean {
            if (email.isBlank()) return false
            val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$")
            return emailRegex.matches(email)
        }

        private fun isValidPassword(senha: String): Boolean {
            if (senha.isBlank()) return false
            return senha.length >= 6 &&
                    senha.any { it.isDigit() } &&
                    senha.any { it.isLetter() }
        }

        private fun calculatePasswordStrength(senha: String): Int {
            if (senha.isBlank()) return 0

            var strength = 0

            // Comprimento mínimo (6 caracteres)
            if (senha.length >= 6) strength++

            // Comprimento bom (8+ caracteres)
            if (senha.length >= 8) strength++

            // Contém números
            if (senha.any { it.isDigit() }) strength++

            // Contém letras maiúsculas e minúsculas
            if (senha.any { it.isUpperCase() } && senha.any { it.isLowerCase() }) strength++

            // Contém caracteres especiais
            if (senha.any { !it.isLetterOrDigit() }) strength++

            // Limita a 4 pontos no máximo
            return strength.coerceAtMost(4)
        }
    }

    fun realizarCadastro(
        onNavigateToCarro: () -> Unit,
        simulateApiCall: (callback: (Boolean) -> Unit) -> Unit
    ): CadastroUiState {
        return if (formularioValido) {
            // Estado de loading
            this.copy(
                isLoading = true,
                mensagemErro = "",
                timestampInicioCadastro = System.currentTimeMillis()
            ).also { loadingState ->
                // Simula a chamada da API
                simulateApiCall { sucesso ->
                    // Esta parte seria tratada no ViewModel
                    // pois envolve mudanças de estado assíncronas
                    if (sucesso) {
                        // O sucesso será tratado pelo ViewModel
                        // que chamará copyComSucesso() e triggerará a navegação
                    } else {
                        // O erro será tratado pelo ViewModel
                    }
                }
            }
        } else {
            // Retorna estado com erro de validação
            this.copy(
                mensagemErro = "Por favor, corrija os erros nos campos acima.",
                tentativasCadastro = tentativasCadastro + 1
            )
        }
    }

    // Versão alternativa mais simples para uso no ViewModel
    fun copyIniciandoCadastro(): CadastroUiState {
        return this.copy(
            isLoading = true,
            mensagemErro = "",
            timestampInicioCadastro = System.currentTimeMillis()
        )
    }

    fun copyComResultadoCadastro(sucesso: Boolean): CadastroUiState {
        return if (sucesso) {
            this.copyComSucesso()
        } else {
            this.copy(
                isLoading = false,
                mensagemErro = "Falha no cadastro. Tente novamente mais tarde.",
                tentativasCadastro = tentativasCadastro + 1,
                timestampFimCadastro = System.currentTimeMillis()
            )
        }
    }

    fun copyComNavegacaoConcluida(): CadastroUiState {
        return this.copy(
            nome = "",
            email = "",
            senha = "",
            confirmarSenha = "",
            termosAceito = false,
            politicaPrivacidadeAceita = false,
            cadastroSucesso = false
        )
    }

    fun realizarCadastro(
        onNavigateToCarro: () -> Unit,
        simulateApiCall: (callback: (Boolean) -> Unit) -> Unit
    ): CadastroUiState {
        return if (formularioValido) {
            // Estado de loading
            this.copy(
                isLoading = true,
                mensagemErro = "",
                timestampInicioCadastro = System.currentTimeMillis()
            ).also { loadingState ->

                simulateApiCall { sucesso ->

                    if (sucesso) {

                    } else {

                    }
                }
            }
        } else {
            // Retorna estado com erro de validação
            this.copy(
                mensagemErro = "Por favor, corrija os erros nos campos acima.",
                tentativasCadastro = tentativasCadastro + 1
            )
        }
    }
    fun copyIniciandoCadastro(): CadastroUiState{
        return this.copy(
            isLoading = true,
            mensagemErro = "",
            timestampInicioCadastro = System.currentTimeMillis()
        )
    }
    fun copyComResultadoCadastro(sucesso: Boolean): CadastroUiState{
        return if (sucesso){
            this.copyComSucesso()
        }else{
            this.copy(
                isLoading = false,
                mensagemErro = "Falha no cadastro. Tente novamente mais tarde.",
                tentativasCadastro = tentativasCadastro + 1,
                timestampFimCadastro = System.currentTimeMillis()
            )
        }
    }
    fun copyComNavegacaoConcluida(): CadastroUiState{
        return this.copy(
            nome = "",
            email = "",
            senha = "",
            confirmarSenha = "",
            termosAceito = false,
            politicaPrivacidadeAceita = false,
            cadastroSucesso = false
        )
    }
}

