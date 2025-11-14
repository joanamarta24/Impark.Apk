package com.example.imparkapk.data.dao.local.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "gerente",
    foreignKeys = [
        ForeignKey(
            entity = ClienteEntity::class,
            parentColumns = ["id"],
            childColumns = ["id_usuario"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ClienteEntity::class,
            parentColumns = ["id"],
            childColumns = ["estacionamento_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class GerenteEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "id_usuario")
    val usuarioId: String,

    @ColumnInfo(name = "estacionamento_id")
    val estacionamentoId: String,

    @ColumnInfo(name = "nivel_acesso")
    val nivelAcesso: Int, // 1 - Gerente, 2 - Supervisor, 3 - Funcionário

    @ColumnInfo(name = "ativo")
    val ativo: Boolean = true,

    @ColumnInfo(name = "data_criacao")
    val dataCriacao: Date,

    @ColumnInfo(name = "data_atualizacao")
    val dataAtualizacao: Date
)
