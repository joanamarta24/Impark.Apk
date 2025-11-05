package com.example.imparkapk.data.dao.remote.api.response

import com.google.gson.annotations.SerializedName
import java.util.Date

data class EstacionamentoResponse(
    @SerializedName("id")
    val id: String,

    @SerializedName("nome")
    val nome:String,

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
    val horarioAbertura:String,

    @SerializedName("horario_fechamento")
    val horaririoFechamento:String,

    @SerializedName("descricao")
    val descricao:String?,

    @SerializedName("fotos")
    val fotos:List<String>,

    @SerializedName("servicos")
    val servicos:List<String>,

    @SerializedName("nota_media")
    val notaMedia:Double,

    @SerializedName("total_avaliacoes")
    val totalAvaliacoes:Int,

    @SerializedName("data_criacao")
    val dataAtualizacao:Date,

    @SerializedName("ativo")
    val ativo: Boolean,

    @SerializedName("dono_id")
    val donoId: String?

)
data class EstacionamentoDetalhesResponse(
    @SerializedName("estacionamento")
    val estacionamento:EstacionamentoResponse,

    @SerializedName("avaliacoes_recentes")
    val avaliacoesRecentes:List<AvaliacaoResponse>,

    @SerializedName("horarios_ocupados")
    val horariosOcupados:List<String>,

    @SerializedName("estatisticas")
    val estatisticas:EstatisticasEstacionamento
)
data class EstatisticasEstacionamento(
    @SerializedName("taxa_ocupacao")
    val taxaOcupacao: Double,

    @SerializedName("avaliacao_media")
    val avaliacaoMedia: Double,

    @SerializedName("total_reservas")
    val totalReservas: Int,

    @SerializedName("faturamento_mensal")
    val faturamentoMensal: Double
)

