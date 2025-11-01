package com.example.imparkapk.data.dao.remote.api.request

import com.google.gson.annotations.SerializedName

data class CarroRequest(
    @SerializedName("usuario_id")
    val usuarioId: String,

    @SerializedName("modelo")
    val modelo: String,

    @SerializedName("placa")
    val placa: String,

    @SerializedName("cor")
    val cor: String

)
