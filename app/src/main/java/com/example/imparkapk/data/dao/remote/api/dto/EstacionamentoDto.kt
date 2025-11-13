package com.example.imparkapk.data.dao.remote.api.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class EstacionamentoDto(
    val id:Long? = null,
    val nome:String,
    val endereco:String,
    val cep:String,
    val cidade:String,
    val estado:String,
    val telefone: String? = null,
    val email:String? = null,
    val capadideTotal:Int,
    val vagasDisponiveis:Int? = null,
    val valorHora:BigDecimal,
    val valorHoraDiaria:BigDecimal? = null,
    val valorMensal:BigDecimal? = null,
    val horarioAbertura:String? = null,
    val horarioFechamento:String? = null,
    val isAtivo:Boolean = true,
    val latitude:Double? = null,
    val longitude:Double? = null,
    val fotos:List<String>? = null,
    val amenities: List<String>? = null,//["COBERTURA","SEGURANCA","LAVAGEM"]
    val notaMedia:Double? = null,
    val totalAvaliacoes: Int? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
)