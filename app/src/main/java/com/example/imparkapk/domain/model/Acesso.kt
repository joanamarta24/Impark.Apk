package com.example.imparkapk.domain.model

import java.util.Date

data class Acesso(

    val id: Long,
    val estacionamento: Estacionamento,
    val carro: Carro?,
    val placa: String,
    val horaDeEntrada: Date,
    val horaDeSaida: Date,
    val horasTotais: Int,
    val valorTotal: Double,
    val ativo: Boolean
)
