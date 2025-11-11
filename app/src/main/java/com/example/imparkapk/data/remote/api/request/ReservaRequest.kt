package com.example.imparkapk.data.remote.api.request

import com.google.gson.annotations.SerializedName

data class ReservaRequest(
    @SerializedName("usuario_id")
    val usuarioId: String,

    @SerializedName("carro_id")
    val carroId: String,

    @SerializedName("estacionamento_id")
    val estacionamentoId:String,

    @SerializedName("data_reserva")
    val dataRserva: String, //ISO STRING

    @SerializedName("hora_entrada")
    val horaEntrada: String,

    @SerializedName("hora_saida")
    val horaSainda:String,

    @SerializedName("valor_total")
    val valorTotal: Double
)
data class AtualizarReservaRequest(
    @SerializedName("status")
    val status: String? = null,

    @SerializedName("hora_entrada_real")
    val horaEntradaReal: String? = null,

    @SerializedName("hora_saida_real")
    val horaSaidaReal:String? = null,

    @SerializedName("valor_total_real")
    val valorTotalReal: Double? = null

)
data class VerificarDisponibilidadeRequest(
    @SerializedName("estacionamento_id")
    val estacionamentoId: String,

    @SerializedName("data")
    val data: String,

    @SerializedName("horario_entrada")
    val horaEntrada: String,

    @SerializedName("hora_saida")
    val horaSainda: String
)