package com.example.imparkapk.domain.model

import com.example.imparkapk.domain.model.usuarios.Dono
import com.example.imparkapk.domain.model.usuarios.Gerente
import java.util.Date

data class Estacionamento(
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
    val reservas: List<Reserva>?,
    val dono: Dono?,
    val gerentes: List<Gerente>?
)

