package com.example.imparkapk.data.dao.local.dao.usuarios

import com.example.imparkapk.data.dao.model.Estacionamento
import com.example.imparkapk.domain.model.Carro
import java.util.Date

data class Acesso(
    val id: Long,
    val estacionamento: Estacionamento?,
    val carro: Carro?,
    val placa: String,
    val horaDeEntrada: Date,
    val horaDeSaida:Date,
    val valorTotal: Double,
    val ativo: Boolean

    )