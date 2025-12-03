package com.example.imparkapk.data.dao.remote.api.dto.estacionamento

import com.google.gson.annotations.SerializedName
import java.util.Date

data class EstacionamentoDTO(
    @SerializedName ("id")
    val id: String,
    val nome: String,
    val endereco: String,
    val cep:String,
    val cidade: String,
    val estado: String,
    val latitude: Double,
    val longitude: Double,
    val telefone: String? = null,
    val email: String? = null,
    val valorHora: Double,
    val valorDiaria: Double? = null,
    val valorMensal: Double? = null,
    val capacidadeTotal: Int,
    val vagasDisponiveis: Int,
    val horarioAbertura: String? = null,
    val horarioFechamento: String? = null,
    val tipo: String,
    val amenities: List<String>,
    val descricao: String? = null,
    val fotos: List<String>,
    val notaMedia: Double? = null,
    val totalAvaliacoes: Int = 0,
    val ativo: Boolean = true,
    val usuarioId: String,
    val dataCriacao: Date,
    val dataAtualizacao: Date
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
