package com.example.imparktcc.data.remote.request

import com.google.gson.annotations.SerializedName

data class CarroRequest(
    @SerializedName("usuario_id")
    val usuarioId: String,

    @SerializedName("modelo")
    val modelo: String,

    @SerializedName("placa")
    val placa: String,

    @SerializedName("cor")
    val cor: String,

    @SerializedName("ano")
    val ano: Int? = null,

    @SerializedName("marca")
    val marca: String? = null
)

data class AtualizarCarroRequest(
    @SerializedName("modelo")
    val modelo: String? = null,

    @SerializedName("placa")
    val placa: String? = null,

    @SerializedName("cor")
    val cor: String? = null,

    @SerializedName("ano")
    val ano: Int? = null
)