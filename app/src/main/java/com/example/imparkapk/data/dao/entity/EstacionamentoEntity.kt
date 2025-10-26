package com.example.imparkapk.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "estacionamentos")
data class EstacionamentoEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "nome")
    val nome: String,

    @ColumnInfo(name = "endereco")
    val endereco: String,

    @ColumnInfo(name = "latitude")
    val latitude: Double,

    @ColumnInfo(name = "longitude")
    val longitude: Double,

    @ColumnInfo(name = "total_vagas")
    val totalVagas: Int,

    @ColumnInfo(name = "vagas_disponiveis")
    val vagasDisponiveis: Int,

    @ColumnInfo(name = "valor_hora")
    val valorHora: Double,

    @ColumnInfo(name = "telefone")
    val telefone: String,

    @ColumnInfo(name = "horario_abertura")
    val horarioAbertura: String, // "08:00"

    @ColumnInfo(name = "horario_fechamento")
    val horarioFechamento: String, // "22:00"

    @ColumnInfo(name = "ativo")
    val ativo: Boolean = true,

    @ColumnInfo(name = "data_criacao")
    val dataCriacao: Date,

    @ColumnInfo(name = "data_atualizacao")
    val dataAtualizacao: Date

)
