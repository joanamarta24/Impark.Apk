package com.example.imparkapk.data.remote.api.request

import com.google.gson.annotations.SerializedName

data class AvaliacaoRequest(
    @SerializedName("usuario_id")
    val usuarioId: String,
    @SerializedName("estabelecimento_id")
    val estabelecimentoId: String,
    @SerializedName("nota")
    val nota: Int,
    @SerializedName("comentario")
    val comentario: String? = null,
    @SerializedName("reserva_id")
    val reservaId: String? = null
)
data class AtualizarAvaliacaoRequest(
    @SerializedName("nota")
    val nota: Int,
    @SerializedName("comentario")
    val comentario: String? = null
)

