package com.example.imparkapk.data.remote.dto

import java.util.Date

data class AcessoDto(
    val id: Long,
    val estacionamentoId: Long,
    val carroId: Long,
    val placa: String,
    val horaDeEntrada: Date,
    val horaDeSaida: Date,
    val horasTotais: Int,
    val valorTotal: Double,
    val ativo: Boolean
)
