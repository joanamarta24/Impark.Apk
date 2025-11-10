package com.example.imparkapk.data.dao.remote.api.response

import com.google.gson.annotations.SerializedName
import java.util.Date

data class GerenteResponse(
    @SerializedName("id")
    val id: String,

    @SerializedName("usuario_id")
    val usuarioId: String,

    @SerializedName("estacionamento_id")
    val estacionamentoId: String,

    @SerializedName("nivel_acesso")
    val nivelAcesso: Int,

    @SerializedName("data_criacao")
    val dataCriacao: Date,

    @SerializedName("data_atualizacao")
    val dataAtualizacao: Date,

    @SerializedName("ativo")
    val ativo: Boolean,

    @SerializedName("usuario")
    val usuario: UsuarioResponse?,

    @SerializedName("estacionamento")
    val estacionamento: EstacionamentoResponse?
)
