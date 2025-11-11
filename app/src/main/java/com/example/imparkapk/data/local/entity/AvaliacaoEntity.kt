package com.example.imparkapk.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.imparkapk.data.local.entity.usuarios.ClienteEntity
import java.util.Date

@Entity(
    tableName = "avaliacoes",
    foreignKeys = [
        ForeignKey(
            entity = ClienteEntity::class,
            parentColumns = ["id"],
            childColumns = ["usuario_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = EstacionamentoEntity::class,
            parentColumns = ["id"],
            childColumns = ["estacionamento_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class AvaliacaoEntity(
    @PrimaryKey
    val id: Long,

    @ColumnInfo(name = "usuario_id")
    val usuarioId: String,

    @ColumnInfo(name = "estacionamento_id")
    val estacionamentoId: String,

    @ColumnInfo(name = "nota")
    val nota: Int,

    @ColumnInfo(name = "comentario")
    val comentario: String,

    @ColumnInfo(name = "data_da_avaliacao")
    val dataAvaliacao: Date,

    @ColumnInfo(name = "ativo")
    val ativo: Boolean
)
