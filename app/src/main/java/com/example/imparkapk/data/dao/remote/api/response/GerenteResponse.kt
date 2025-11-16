package com.example.imparkapk.data.dao.remote.api.response

import com.example.imparkapk.data.dao.model.enus.TipoVeiculo
import com.google.gson.annotations.SerializedName
import java.util.Date
import kotlin.collections.mapKeys

data class GerenteResponse(
    @SerializedName("id")
    val id: String,

    @SerializedName("usuario_id")
    val usuarioId: String,

    @SerializedName("estacionamento_id")
    val estacionamentoId: String,

    @SerializedName("nivel_acesso")
    val nivelAcesso: Int,

    @SerializedName("data_criacao")
    val dataCriacao: Date,

    @SerializedName("data_atualizacao")
    val dataAtualizacao: Date,

    @SerializedName("ativo")
    val ativo: Boolean,

    @SerializedName("usuario")
    val usuario: UsuarioResponse?,

    @SerializedName("estacionamento")
    val estacionamento: EstacionamentoResponse?
){
    // Métodos de utilidade

    fun isAtivo(): Boolean = ativo
    fun hasFullAccess(): Boolean = nivelAcesso ==1
    fun hasLimitedAccess():Boolean = nivelAcesso == 2
    fun getEmailGerente(): String? = usuario?.email

    fun getNomeEstacionamento(): String? = estacionamento?.nome

    fun podeGerenciarFuncionarios(): Boolean = nivelAcesso <= 2

    fun podeGerenciarFinanceiro(): Boolean = nivelAcesso == 1

    // Método para converter datas para formato legível
    fun getDataCriacaoFormatada(): String{
        return android.text.format.DateFormat.format("dd/MM/yyyy HH:mm",dataCriacao).toString()
    }
    fun getDataAtualizacaoFormatada(): String {
        return android.text.format.DateFormat.format("dd/MM/yyyy HH:mm", dataAtualizacao).toString()
    }
}

// Classes complementares necessárias

data class ClienteReponse(
    @SerializedName("id")
    val id: String,

    @SerializedName("nome")
    val nome: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("telefone")
    val telefone: String?,

    @SerializedName("cpf")
    val cpf: String?,

    @SerializedName("data_nascimento")
    val dataNascimento: Date?,

    @SerializedName("foto_url")
    val fotoUrl: String?,

    @SerializedName("ativo")
    val ativo: Boolean
)
// Extension functions
fun GerenteResponse.getEnderecoCompleto(): String? {
    return estacionamento?.endereco?.let { endereco ->
        "${endereco.logradouro}, ${endereco.numero}" +
                (endereco.complemento?.let { " - $it" } ?: "") +
                " - ${endereco.bairro}, ${endereco.cidade} - ${endereco.estado}, ${endereco.cep}"
    }
}

fun GerenteResponse.getVagasDisponiveis(): Int {
    return (estacionamento?.totalVagas ?: 0) - (estacionamento?.vagasOcupadas ?: 0)
}

fun GerenteResponse.getTaxaOcupacao(): Double {
    val total = estacionamento?.totalVagas ?: 0
    val ocupadas = estacionamento?.vagasOcupadas ?: 0
    return if (total > 0) (ocupadas.toDouble() / total.toDouble()) * 100 else 0.0
}

// Companion object com constantes úteis
companion object {
    const val NIVEL_ACESSO_TOTAL = 1
    const val NIVEL_ACESSO_PARCIAL = 2
    const val NIVEL_ACESSO_BASICO = 3
}





