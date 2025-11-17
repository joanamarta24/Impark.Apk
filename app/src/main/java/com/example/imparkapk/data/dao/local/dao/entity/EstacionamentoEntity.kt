package com.example.imparkapk.data.dao.local.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.imparkapk.data.dao.model.Estacionamento
import java.util.Date

@Entity(tableName = "estacionamentos")
data class EstacionamentoEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo( "nome")
    val nome: String,

    @ColumnInfo("endereco")
    val endereco: String,

    @ColumnInfo( "latitude")
    val latitude: Double,

    @ColumnInfo( "longitude")
    val longitude: Double,

    @ColumnInfo( "total_vagas")
    val totalVagas: Int,

    @ColumnInfo( "vagas_disponiveis")
    val vagasDisponiveis: Int,

    @ColumnInfo( "valor_hora")
    val valorHora: Double,

    @ColumnInfo()

    @ColumnInfo( "telefone")
    val telefone: String,

    @ColumnInfo( "horario_abertura")
    val horarioAbertura: String, // "08:00"

    @ColumnInfo("horario_fechamento")
    val horarioFechamento: String, // "22:00"

    @ColumnInfo( "ativo")
    val ativo: Boolean = true,

    @ColumnInfo( "data_criacao")
    val dataCriacao: Date,

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
fun Estacionamento.toEstacionamentoEntity() = EstacionamentoEntity(
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
