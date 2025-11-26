package com.example.imparkapk.data.dao.local.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(
    tableName = "gerentes",
    foreignKeys = [
        ForeignKey(
            entity = EstacionamentoEntity::class,
            parentColumns = ["id"],
            childColumns = ["estacionamento_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["email"], unique = true),
        Index(value = ["cpf"], unique = true),
        Index(value = ["estacionamento_id"])
    ],

)
data class GerenteEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "nome")
    val nome: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "senha")
    val senha: String,

    @ColumnInfo(name = "cpf")
    val cpf: String,

    @ColumnInfo(name = "telefone")
    val telefone: String,

    @ColumnInfo(name = "usuario_id")
    val usuarioId: String? = null,

    @ColumnInfo(name = "estacionamento_id")
    val estacionamentoId: String,

    @ColumnInfo(name = "nivel_acesso")
    val nivelAcesso: Int = 1,

    @ColumnInfo(name = "ativo")
    val ativo: Boolean = true,

    @ColumnInfo(name = "data_criacao")
    val dataCriacao: Date = Date(),

    @ColumnInfo(name = "data_atualizacao")
    val dataAtualizacao: Date = Date()
)