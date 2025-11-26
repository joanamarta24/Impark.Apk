package com.example.imparkapk.data.dao.remote.api.response

import com.example.imparkapk.data.dao.model.enus.Permissao


data class GerenteResponse(
    val id: String,
    val nome: String,
    val email: String,
    val cpf: String,
    val telefone: String,
    val nivelAcesso: Int,
    val estacionamento: EstacionamentoInfo? = null,
    val dataCriacao: String,
    val ativo: Boolean = true
)
data class EstacionamentoInfo(
    val id: String,
    val nome: String,
    val endereco: Endereco?,
    val totalVagas: Int,
    val vagasOcupadas: Int
)

data class Endereco(
    val logradouro: String,
    val numero: String,
    val complemento: String? = null,
    val bairro: String,
    val cidade: String,
    val estado: String,
    val cep: String
) {
    // Extension functions
    fun GerenteResponse.getEnderecoCompleto(): String? {
        return estacionamento?.endereco?.let { endereco ->
            buildString {
                append("${endereco.logradouro}, ${endereco.numero}")
                endereco.complemento?.let { append(" - $it") }
                append(" - ${endereco.bairro}, ${endereco.cidade} - ${endereco.estado}, ${endereco.cep}")
            }
        }
    }

    fun GerenteResponse.getVagasDisponiveis(): Int {
        return (estacionamento?.totalVagas ?: 0) - (estacionamento?.vagasOcupadas ?: 0)
    }

    fun GerenteResponse.getTaxaOcupacao(): Double {
        val total = estacionamento?.totalVagas ?: 0
        val ocupadas = estacionamento?.vagasOcupadas ?: 0
        return if (total > 0) {
            (ocupadas.toDouble() / total.toDouble()) * 100
        } else {
            0.0
        }
    }

    fun GerenteResponse.getTaxaOcupacaoFormatada(): String {
        return "%.1f%%".format(getTaxaOcupacao())
    }


    fun GerenteResponse.getNivelAcesso(): String {
        return when (nivelAcesso) {
            GerenteResponse.NICEL_ACESSO_TOTAL -> "Acesso Total"
            GerenteResponse.NIVEL_ACESSO_PARCIAL -> "Acesso Parcial"
            GerenteResponse.NIVEL_ACESSO_BASICO -> "Acesso Básico"
            else -> "Desconhecido"
        }
    }


    fun GerenteResponse.podeGerenciarFuncionarios(): Boolean {
        return nivelAcesso <= GerenteResponse.NIVEL_ACESSO_PARCIAL
    }

    fun GerenteResponse.podeAlterarConfiguracoes(): Boolean {
        return nivelAcesso == GerenteResponse.NIVEL_ACESSO_TOTAL
    }


    // Companion object dentro da classe
    companion object {
        const val NIVEL_ACESSO_TOTAL = 1
        const val NIVEL_ACESSO_PARCIAL = 2
        const val NIVEL_ACESSO_BASICO = 3
    }

    enum class NivelAcesso(
        val valor: Int,
        val descricao: String,
        val permissoes: Set<Permissao>
    ) {
        TOTAL(
            valor = 1,
            descricao = "Acesso Total",
            permissoes = setOf(
                Permissao.GERENCIAR_FUNCIONARIOS,
                Permissao.ALTERAR_CONFIGURACOES,
                Permissao.VISUALIZAR_RELATORIOS,
                Permissao.GERENCIAR_ESTACIONAMENTO,
                Permissao.ACESSO_FINANCEIRO
            )
        ),

        PARCIAL(
            valor = 2,
            descricao = "Acesso Parcial",
            permissoes = setOf(
                Permissao.GERENCIAR_FUNCIONARIOS,
                Permissao.VISUALIZAR_RELATORIOS,
                Permissao.GERENCIAR_ESTACIONAMENTO
            )
        ),

        BASICO(
            valor = 3,
            descricao = "Acesso Básico",
            permissoes = setOf(
                Permissao.VISUALIZAR_RELATORIOS,
                Permissao.GERENCIAR_ESTACIONAMENTO
            )
        );

        companion object {
            fun fromValor(valor: Int): NivelAcesso? {
                return values().find { it.valor == valor }
            }
        }
    }
}


