package com.example.imparkapk.data.remote.api.request

import com.google.gson.annotations.SerializedName

data class GerenteRequest(
    @SerializedName("usuario_id")
    val usuarioId: String,
    @SerializedName("esracionamento_id")
    val estacionamentoId: String,
    @SerializedName("nivel_acesso")
    val nivelAcesso: Int
)
data class AtualizarGerenteRequest(
    @SerializedName("nivel_acesso")
    val nivelAcesso: Int? = null,

    @SerializedName("ativo")
    val ativo: Boolean? = null
)
