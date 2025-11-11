package com.example.imparkapk.data.remote.api.response

import com.google.gson.annotations.SerializedName
import java.util.Date

data class UsuarioResponse(
    @SerializedName("id")
    val id: String,

    @SerializedName("nome")
    val nome: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("telefone")
    val telefone: String?,

    @SerializedName("cpf")
    val cpf: String?,

    @SerializedName("foto_perfil")
    val fotoPerfil: String?,

    @SerializedName("data_criacao")
    val dataCriacao: Date,

    @SerializedName("data_atualizacao")
    val dataAtualizacao: Date,

    @SerializedName("ativo")
    val ativo: Boolean,

    @SerializedName("ultimo_acesso")
    val ultimoAcesso: Date?,

    @SerializedName("tipo_usuario")
    val tipoUsuario: String = "cliente"
)
data class LoginResponse(
    @SerializedName("usuario")
    val usuario: UsuarioResponse,
    @SerializedName("token")
    val tokrn: String,
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("expira_em")
    val expiraEm: Date
)
data class  RecuperacaoSenhaResponse(
    @SerializedName("mensagem")
    val mensagem: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("expira_em")
    val expiraEm: Date
)
