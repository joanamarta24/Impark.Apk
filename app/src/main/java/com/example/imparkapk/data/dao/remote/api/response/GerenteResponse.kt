package com.example.imparkapk.data.dao.remote.api.response


data class GerenteResponse(
    val id: String,
    val usuarioId: String,
    val nome: String,
    val email: String,
    val estacionamentoId: String,
    val nivelAcesso: Int,
    val ativo: Boolean,
    val dataCriacao: String?,
    val dataAtualizacao: String?,
    val cpf: String? = "",
    val telefone: String? = "",
    val dataNascimento: String? = null,
    val estacionamento

)
data class GerenteRequest(
    val usuarioId: String,
    val nome: String,
    val email: String,
    val estacionamentoId: String,
    val nivelAcesso: Int,
    val cpf: String? = "",
    val telefone: String? = ""
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
}



