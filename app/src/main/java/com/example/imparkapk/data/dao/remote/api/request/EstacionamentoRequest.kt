package com.example.imparkapk.data.dao.remote.api.request

import com.google.gson.annotations.SerializedName

data class EstacionamentoRequest (
    @SerializedName("nome")
    val nome: String,

    @SerializedName("endereco")
    val endereco:String,

    @SerializedName("latitude")
    val latitude:Double,

    @SerializedName("longitude")
    val longitude:Double,

    @SerializedName("total_vagas")
    val totalVagas:Int,

    @SerializedName("vagas_disponiveis")
    val vagasDisponiveis:Int,

    @SerializedName("valor_hora")
    val valorHora:Double,


    @SerializedName("telefone")
    val telefone:String,
    @SerializedName("horario_abertura")
    val horarioAbertura: String,

    @SerializedName("horario_fechamento")
    val horarioFechamento: String,

    @SerializedName("descricao")
    val descricao:String? = null,

    @SerializedName("fotos")
    val fotos:List<String>? =null,

    @SerializedName("servicos")
    val servicos: List<String>? = null
)
data class AtualizarEstacionamentoReques(
    @SerializedName("nome")
    val nome: String? = null,

    @SerializedName("endereco")
    val endereco: String? = null,

    @SerializedName("valor_hora")
    val valorHora: Double? = null,

    @SerializedName("telefone")
    val telefone: String? = null,

    @SerializedName("horario_abertura")
    val horarioAbertura: String? = null,

    @SerializedName("horario_fechamento")
    val horarioFechamento: String? = null,

    @SerializedName("vagas_disponiveis")
    val vagasDisponiveis: Int? = null
)

data class BuscarEstacionamentosRequest(
    @SerializedName("latitude")
    val latitude: Double? = null,

    @SerializedName("longitude")
    val longitude: Double? = null,

    @SerializedName("raio_km")
    val raioKm: Double? = null,

    @SerializedName("query")
    val query: String? = null,

    @SerializedName("valor_maximo")
    val valorMaximo: Double? = null,

    @SerializedName("apenas_com_vagas")
    val apenasComVagas: Boolean = false,

    @SerializedName("servicos")
    val servicos: List<String>? = null
)
