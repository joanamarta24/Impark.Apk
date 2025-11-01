package com.example.imparkapk.data.dao.remote.api.request

import com.google.gson.annotations.SerializedName

data class UsuarioRequest(
    @SerializedName("nome")
    val nome: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("senha")
    val senha: String
)
