package com.example.imparkapk.data.dao.response

import com.google.gson.annotations.SerializedName
import java.util.Date

data class UsuarioResponse(
    @SerializedName("id")
    val id: String,

    @SerializedName("nome")
    val nome: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("data_criacao")
    val dataCriacao: Date,

    @SerializedName("data_atualizacao")
    val dataAtualizacao: Date,

    @SerializedName("ativo")
    val ativo: Boolean
)
