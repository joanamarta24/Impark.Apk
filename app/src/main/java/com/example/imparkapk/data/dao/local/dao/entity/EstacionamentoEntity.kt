package com.example.imparkapk.data.dao.local.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.imparkapk.data.dao.model.Estacionamento
import com.google.gson.Gson
import java.util.Date

@Entity(tableName = "estacionamentos")
data class EstacionamentoEntity(
    @PrimaryKey val id: String,

    val nome: String,
    val endereco: String,
    val latitude: Double,
    val longitude: Double,
    val totalVagas: Int,
    val vagasDisponiveis: Int,
    val valorHora: Double,
    val telefone: String,
    val horarioAbertura: String,
    val horarioFechamento: String,
    val ativo: Boolean = true,
    val dataCriacao: Date,
    val amenities : String? = null,

    @ColumnInfo("data_atualizacao")
    val dataAtualizacao: Date,
    val vagasTotal: Int,
    val precoHora: Double,
    val horarioFuncionamento: String

)
data class Estacionamento(
    val id: String,
    val  nome: String,
    val endereco: String,
    val latitude: Double,
    val longitude: Double,
    val valorHora: Double,
    val vagasDisponiveis: Int,
    val vagasTotal: Int,
    val ativo: Boolean = true
)
//  EXTENSIONS FUNCTIONS PARA CONVERSÃO
fun EstacionamentoEntity.toEstacionamento() = Estacionamento(
    id = this.id,
    nome = this.nome,
    endereco = this.endereco,
    latitude = this.latitude,
    longitude = this.longitude,
    valorHora = this.valorHora,
    vagasDisponiveis = this.vagasDisponiveis,
    vagasTotal = this.vagasTotal,
    ativo = this.ativo

)
