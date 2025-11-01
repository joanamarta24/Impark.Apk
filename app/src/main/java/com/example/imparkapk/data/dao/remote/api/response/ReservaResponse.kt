package com.example.imparkapk.data.dao.remote.api.response

import com.example.imparktcc.model.Carro
import com.google.gson.annotations.SerializedName
import java.util.Date

data class ReservaResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("usuario_id")
    val usuarioId: String,
    @SerializedName("carro_id")
    val carroId: String,
    @SerializedName("estacionamento_id")
    val estacionamentoId: String,
    @SerializedName("data_reserva")
    val dataReserva: Date,
    @SerializedName("hora_entrada")
    val horaEntrada: Date,
    @SerializedName("hora_saida")
    val horaSaida: Date,
    @SerializedName("valor_total")
    val valorTotal: Double,
    @SerializedName("status")
    val status: String,
    @SerializedName("codigo_reserva")
    val codigoReserva: String,
    @SerializedName("data_criacao")
    val dataCriacao: Date,
    @SerializedName("data_atualizacao")
    val dataAtualizacao: Date,
    @SerializedName("carro")
    val carro: Carro,
    @SerializedName("estacionamento")
    val estacionamento: EstacionamentoResponse
)
data class DisponibilidadeResponse(
    @SerializedName("disponivel")
    val disponivel: Boolean,

    @SerializedName("horarios_ocupados")
    val horariosOcupados: List<String>,

    @SerializedName("valor_estimado")
    val valorEstimado: Double,

    @SerializedName("mensagem")
    val mensagem: String?
)
data class ConfirmacaoReservaResponse(
    @SerializedName("reserva")
    val reserva: ReservaResponse,

    @SerializedName("qr_code")
    val qrCode: String,

    @SerializedName("instrucoes")
    val instrucoes: List<String>
)
data class VerificarDisponibilidadeResponse(
    @SerializedName("estacionamento_id")
    val estacionamentoId: String,

    @SerializedName("data_reserva")
    val dataReserva: Date,

    @SerializedName("hora_entrada")
    val horaEntrada: Date,

    @SerializedName("hora_saida")
    val horaSaida: Date,

    @SerializedName("carro_id")
    val carroId: String? = null,

    @SerializedName("usuario_id")
    val usuarioId: String? = null
)
data class AtualizarReservaRequest(
    @SerializedName("reserva_id")
    val reservaId: String,

    @SerializedName("nova_hora_entrada")
    val novaHoraEntrada: Date? = null,

    @SerializedName("nova_hora_saida")
    val novaHoraSaida: Date? = null,

    @SerializedName("novo_carro_id")
    val novoCarroId: String? = null,

    @SerializedName("novo_estacionamento_id")
    val novoEstacionamentoId: String? = null,

    @SerializedName("novo_status")
    val novoStatus: String? = null,

    @SerializedName("observacao")
    val observacao: String? = null
)

