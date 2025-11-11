package com.example.imparkapk.data.remote.api.request

import com.google.gson.annotations.SerializedName

data class UsuarioRequest(
    @SerializedName("nome")
    val nome: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("senha")
    val senha: String,

    @SerializedName("telefone")
    val telefone:String? = null,

    @SerializedName("cpf")
    val cpf:String? = null
)
data class LoginRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("senha")
    val senha: String,
    @SerializedName("device_id")
    val deviceId:String? = null
)
data class RecuperacaoSenhaRequest(
        @SerializedName("email")
        val email: String
)
data class VerificarCodigoRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("codigo")
    val codigo :String
)
data class RedefinirSenhaRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("codigo")
    val codigo: String,
    @SerializedName("nova_senha")
    val novaSenha:String
)
data class AtualizarUsuarioRequest(
    @SerializedName ("nome")
    val nome: String? = null,
    @SerializedName("telefone")
    val telefone: String? = null,
    @SerializedName ("foto_perfil")
    val fotoPerfil:String? =null
)
