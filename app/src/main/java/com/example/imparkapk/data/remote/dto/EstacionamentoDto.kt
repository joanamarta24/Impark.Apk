package com.example.imparkapk.data.remote.dto

import java.util.Date

data class EstacionamentoDto(
    val id: Long,
    val nome: String,
    val endereco: String,
    val latitude: Double,
    val longitude: Double,
    val totalVagas: Int,
    val vagasDisponiveis: Int,
    val valorHora: Double,
    val telefone: String,
    val horarioAbertura: Date,
    val horarioFechamento: Date,
    val ativo: Boolean = true,
    val vagasTotal: Int,
    val precoHora: Double,
    val horarioFuncionamento: String,
    val reservasId: List<Long>,
    val donoId: Long,
    val gerentesId: List<Long>
)