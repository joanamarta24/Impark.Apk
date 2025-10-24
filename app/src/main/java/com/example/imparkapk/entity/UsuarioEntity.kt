package com.example.imparkapk.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName="usuarios")
data class UsuarioEntity(
    @PrimaryKey
    val id:String,
    @ColumnInfo(name = "nome")
    val nome: String,
    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "senha")
    val senha: String,

    @ColumnInfo(name = "data_criacao")
    val dataCriacao: Date,

    @ColumnInfo(name = "data_atualizacao")
    val dataAtualizacao: Date,

    @ColumnInfo(name = "ativo")
    val ativo: Boolean = true
)