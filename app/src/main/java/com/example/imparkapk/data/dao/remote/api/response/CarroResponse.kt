package com.example.imparkapk.data.dao.remote.api.response

import com.google.gson.annotations.SerializedName
import java.util.Date

data class CarroResponse(
    @SerializedName("id")
    val id: String,

    @SerializedName("usuario_id")
    val usuarioId: String,

    @SerializedName("modelo")
    val modelo: String,

    @SerializedName("placa")
    val placa: String,

    @SerializedName("cor")
    val cor: String,

    @SerializedName("data_criacao")
    val dataCriacao: Date,

    @SerializedName("data_atualizacao")
    val dataAtualizacao: Date,

    @SerializedName("ativo")
    val ativo: Boolean
)
