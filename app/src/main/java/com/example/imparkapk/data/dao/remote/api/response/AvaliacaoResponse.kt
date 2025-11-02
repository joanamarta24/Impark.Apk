package com.example.imparkapk.data.dao.remote.api.response

import com.google.gson.annotations.SerializedName
import java.util.Date

data class AvaliacaoResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("usuario_id")
    val usuarioId: String,
    @SerializedName("nota")
    val nota: Int,
    @SerializedName("comentario")
    val comentario: String?,
    @SerializedName("data_avaliacao")
    val dataAvaliacao:Date,
    @SerializedName("data_criacao")
    val dataCriacao: Date,
    @SerializedName("data_atualizacao")
    val dataAtualizacao: Date,
    @SerializedName("usuario")
    val usuario: UsuarioResponse?,
    @SerializedName("reserva_id")
    val reservaId: String?
)
data class EstatisticasAvaliacaoResponse(
    @SerializedName("estacionamento_id")
    val estacionamentoId: String,

    @SerializedName("nota_media")
    val notaMedia: Double,

    @SerializedName("total_avaliacoes")
    val totalAvaliacoes: Int,

    @SerializedName("distribuicao_notas")
    val distribuicaoNotas: Map<Int, Int>,

    @SerializedName("avaliacoes_recentes")
    val avaliacoesRecentes: List<AvaliacaoResponse>
)