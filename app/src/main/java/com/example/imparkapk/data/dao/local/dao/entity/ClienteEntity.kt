package com.example.imparkapk.data.dao.local.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.imparkapk.data.dao.local.dao.db.DateConverter
import java.util.Date
import java.util.UUID

@Entity(tableName = "usuarios")
data class ClienteEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "nome")
    val nome: String,
    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "senha")
    val senha: String,

    @ColumnInfo(name = "data_criacao")
    val dataCriacao: Date,

    @ColumnInfo(name = "data_atualizacao")
    val dataAtualizacao: Date,

    @ColumnInfo(name = "ativo")
    val ativo: Boolean = true
)
@Entity(
    tableName = "usuarios",
    indices = [
        Index(value = ["email"], unique = true),
        Index(value = ["cpf"], unique = true),
        Index(value = ["telefone"], unique = true),
        Index(value = ["ativo"]),
        Index(value = ["tipo_usuario"])
    ]
)
@TypeConverters(DateConverter::class)
data class ClienteEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "nome")
    val nome: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "senha")
    val senha: String,

    @ColumnInfo(name = "telefone")
    val telefone: String? = null,

    @ColumnInfo(name = "cpf")
    val cpf: String? = null,

    @ColumnInfo(name = "data_nascimento")
    val dataNascimento: Date? = null,

    @ColumnInfo(name = "foto_perfil")
    val fotoPerfil: String? = null,

    @ColumnInfo(name = "tipo_usuario")
    val tipoUsuario: TipoUsuario = TipoUsuario.CLIENTE,

    @ColumnInfo(name = "data_criacao")
    val dataCriacao: Date = Date(),

    @ColumnInfo(name = "data_atualizacao")
    val dataAtualizacao: Date = Date(),

    @ColumnInfo(name = "ultimo_acesso")
    val ultimoAcesso: Date? = null,

    @ColumnInfo(name = "ativo")
    val ativo: Boolean = true,

    @ColumnInfo(name = "email_verificado")
    val emailVerificado: Boolean = false,

    @ColumnInfo(name = "telefone_verificado")
    val telefoneVerificado: Boolean = false,

    @ColumnInfo(name = "termos_aceitos")
    val termosAceitos: Boolean = false,

    @ColumnInfo(name = "politica_privacidade_aceita")
    val politicaPrivacidadeAceita: Boolean = false,

    @ColumnInfo(name = "notificacoes_ativas")
    val notificacoesAtivas: Boolean = true,

    @ColumnInfo(name = "versao")
    val versao: Int = 1
) {
    /**
     * Enum que define os tipos de usuário no sistema
     */
    enum class TipoUsuario {
        CLIENTE,
        GERENTE,
        ADMIN
    }

    /**
     * Verifica se o usuário é um cliente
     */
    val isCliente: Boolean
        get() = tipoUsuario == TipoUsuario.CLIENTE

    /**
     * Verifica se o usuário é um gerente
     */
    val isGerente: Boolean
        get() = tipoUsuario == TipoUsuario.GERENTE

    /**
     * Verifica se o usuário é um administrador
     */
    val isAdmin: Boolean
        get() = tipoUsuario == TipoUsuario.ADMIN

    /**
     * Verifica se o usuário está completamente verificado
     */
    val isVerificado: Boolean
        get() = emailVerificado && (telefoneVerificado || telefone == null)

    /**
     * Verifica se o usuário aceitou todos os termos necessários
     */
    val termosCompletos: Boolean
        get() = termosAceitos && politicaPrivacidadeAceita

    /**
     * Cria uma cópia do usuário com a data de atualização atualizada
     */
    fun copyComAtualizacao(): ClienteEntity {
        return this.copy(dataAtualizacao = Date())
    }

    /**
     * Cria uma cópia do usuário com o último acesso atualizado
     */
    fun copyComUltimoAcesso(): ClienteEntity {
        return this.copy(ultimoAcesso = Date())
    }

    /**
     * Cria uma cópia do usuário marcando o e-mail como verificado
     */
    fun copyComEmailVerificado(): ClienteEntity {
        return this.copy(
            emailVerificado = true,
            dataAtualizacao = Date()
        )
    }

    /**
     * Cria uma cópia do usuário marcando o telefone como verificado
     */
    fun copyComTelefoneVerificado(): ClienteEntity {
        return this.copy(
            telefoneVerificado = true,
            dataAtualizacao = Date()
        )
    }

    /**
     * Cria uma cópia do usuário com nova senha
     */
    fun copyComNovaSenha(novaSenha: String): ClienteEntity {
        return this.copy(
            senha = novaSenha,
            dataAtualizacao = Date()
        )
    }

    /**
     * Valida os dados básicos do usuário
     */
    fun validar(): Boolean {
        return nome.isNotBlank() &&
                email.isNotBlank() &&
                senha.isNotBlank() &&
                nome.length in 2..100 &&
                email.length <= 150 &&
                (telefone == null || telefone.length <= 20) &&
                (cpf == null || cpf.length == 11) &&
                termosCompletos
    }

    /**
     * Retorna as iniciais do usuário para avatar
     */
    fun getIniciais(): String {
        return nome.split(" ")
            .take(2)
            .joinToString("") { it.firstOrNull()?.toString() ?: "" }
            .uppercase()
    }

    /**
     * Retorna o primeiro nome do usuário
     */
    fun getPrimeiroNome(): String {
        return nome.split(" ").firstOrNull() ?: nome
    }

    companion object {
        /**
         * Cria um novo usuário com dados básicos
         */
        fun criar(
            nome: String,
            email: String,
            senha: String,
            telefone: String? = null,
            cpf: String? = null,
            dataNascimento: Date? = null,
            tipoUsuario: TipoUsuario = TipoUsuario.CLIENTE
        ): ClienteEntity {
            return ClienteEntity(
                nome = nome.trim(),
                email = email.trim().lowercase(),
                senha = senha,
                telefone = telefone?.trim(),
                cpf = cpf?.trim(),
                dataNascimento = dataNascimento,
                tipoUsuario = tipoUsuario,
                termosAceitos = true,
                politicaPrivacidadeAceita = true
            )
        }

        /**
         * Cria um usuário de demonstração para testes
         */
        fun criarDemo(): ClienteEntity {
            return ClienteEntity(
                nome = "João Silva Santos",
                email = "joao.silva@email.com",
                senha = "senha123",
                telefone = "(11) 99999-9999",
                cpf = "12345678901",
                tipoUsuario = TipoUsuario.CLIENTE,
                emailVerificado = true,
                telefoneVerificado = true,
                termosAceitos = true,
                politicaPrivacidadeAceita = true
            )
        }

        /**
         * Valida o formato do e-mail
         */
        fun validarEmail(email: String): Boolean {
            val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$")
            return emailRegex.matches(email) && email.length in 5..150
        }

        /**
         * Valida o formato do telefone
         */
        fun validarTelefone(telefone: String): Boolean {
            val telefoneRegex = Regex("^\\([1-9]{2}\\) [0-9]{4,5}-[0-9]{4}$")
            return telefoneRegex.matches(telefone) && telefone.length <= 20
        }

        /**
         * Valida o formato do CPF
         */
        fun validarCPF(cpf: String): Boolean {
            val cpfRegex = Regex("^[0-9]{11}$")
            return cpfRegex.matches(cpf) && validarCPFDigitos(cpf)
        }

        /**
         * Valida os dígitos verificadores do CPF
         */
        private fun validarCPFDigitos(cpf: String): Boolean {
            if (cpf.length != 11) return false

            // Verifica se todos os dígitos são iguais
            if (cpf.all { it == cpf[0] }) return false

            // Calcula primeiro dígito verificador
            var soma = 0
            for (i in 0..8) {
                soma += (cpf[i] - '0') * (10 - i)
            }
            var resto = soma % 11
            val digito1 = if (resto < 2) 0 else 11 - resto

            // Calcula segundo dígito verificador
            soma = 0
            for (i in 0..9) {
                soma += (cpf[i] - '0') * (11 - i)
            }
            resto = soma % 11
            val digito2 = if (resto < 2) 0 else 11 - resto

            return (cpf[9] - '0' == digito1) && (cpf[10] - '0' == digito2)
        }
    }
}

/**
 * Extension function para converter Entity para Model (se necessário)
 */
fun ClienteEntity.toUsuario(): com.example.imparkapk.domain.model.Usuario {
    return com.example.imparkapk.domain.model.Usuario(
        id = this.id,
        nome = this.nome,
        email = this.email,
        telefone = this.telefone,
        cpf = this.cpf,
        dataNascimento = this.dataNascimento,
        fotoPerfil = this.fotoPerfil,
        tipoUsuario = when (this.tipoUsuario) {
            ClienteEntity.TipoUsuario.CLIENTE -> com.example.imparkapk.domain.model.Usuario.TipoUsuario.CLIENTE
            ClienteEntity.TipoUsuario.GERENTE -> com.example.imparkapk.domain.model.Usuario.TipoUsuario.GERENTE
            ClienteEntity.TipoUsuario.ADMIN -> com.example.imparkapk.domain.model.Usuario.TipoUsuario.ADMIN
        },
        dataCriacao = this.dataCriacao,
        dataAtualizacao = this.dataAtualizacao,
        ultimoAcesso = this.ultimoAcesso,
        ativo = this.ativo,
        emailVerificado = this.emailVerificado,
        telefoneVerificado = this.telefoneVerificado
    )
}