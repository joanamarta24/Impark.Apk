package com.example.imparkapk.data.dao.model

import java.util.Date
enum class StatusReserva{
    PENDENTE,
    CONFIRMADA,
    CANCELADA,
    FINALIZADA
}
data class Reserva(
    val id:String = "",
    val usuarioId:String ="",
    val carroId:String = "",
    val estacionamentoId:String = "",
    val dataReserva: Date = Date(),
    val horaEntrada:String = "",
    val horaSaida:String = "",
    val valorTotal: Double =0.0,
    val status:String = "pendente",

    val codigoReserva:String = ""
){
    val estaAtiva: Boolean get() = status == "ativa"
    val podeCancelar: Boolean get() = status == "pendente" || status == "confirmada"
    val estaConcluida: Boolean get() = status == "concluida"
}
