package com.example.imparkapk.data.dao.remote.api.dto.estacionamento

import com.google.gson.annotations.SerializedName
import java.util.Date

data class EstacionamentoDTO(
    @SerializedName ("id") val id: String,
    @SerializedName ("nome") val nome: String,
    @SerializedName ("endereco") val endereco: String,
    @SerializedName ("cep") val cep:String,
    @SerializedName ("cidade") val cidade: String,
    @SerializedName ("estado") val estado: String,
    @SerializedName ("latitude") val latitude: Double,
    @SerializedName ("longitude") val longitude: Double,
    @SerializedName ("telefone") val telefone: String? = null,
    @SerializedName ("email") val email: String? = null,
    @SerializedName ("valorHora") val valorHora: Double,
    @SerializedName ("valorDiaria") val valorDiaria: Double? = null,
    @SerializedName("valorMensal") val valorMensal: Double? = null,
    @SerializedName("capacidadeTotal") val capacidadeTotal: Int,
    @SerializedName("vagasDisponiveis") val vagasDisponiveis: Int,
    @SerializedName("horarioAbertura") val horarioAbertura: String? = null,
    @SerializedName("horarioFechamento") val horarioFechamento: String? = null,
    @SerializedName("tipo") val tipo: String,
    @SerializedName("amenities") val amenities: List<String>,
    @SerializedName("descricao") val descricao: String? = null,
    @SerializedName("fotos") val fotos: List<String>,
    @SerializedName("notaMedia") val notaMedia: Double? = null,
    @SerializedName("totalAvaliacoes") val totalAvaliacoes: Int = 0,
    @SerializedName("ativo") val ativo: Boolean = true,
    @SerializedName("usuarioId") val usuarioId: String,
    @SerializedName("dataCriacao") val dataCriacao: Date,
    @SerializedName("dataAtualizacao") val dataAtualizacao: Date
)

data class AvaliacaoDto(
    @SerializedName("id") val id: String,
    @SerializedName("nota") val nota: Int,
    @SerializedName("comentario") val comentario: String? = null,
    @SerializedName("titulo") val titulo: String? = null,
    @SerializedName("usuarioNome") val usuarioNome: String,
    @SerializedName("usuarioFoto") val usuarioFoto: String? = null,
    @SerializedName("dataAvaliacao") val dataAvaliacao: Date,
    @SerializedName("respostaGestor") val respostaGestor: String? = null,
    @SerializedName("dataResposta") val dataResposta: Date? = null


)
