package com.example.imparkapk.data.local.entity.crossRefs.estacionamentoWithAvaliacao

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "estacionamento_avaliacao_cross_ref",
    primaryKeys = ["estacionamentoId", "avaliacaoId"],
    foreignKeys = [
        ForeignKey(
            entity = com.example.imparkapk.data.local.entity.EstacionamentoEntity::class,
            parentColumns = ["id"],
            childColumns = ["estacionamentoId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = com.example.imparkapk.data.local.entity.AvaliacaoEntity::class,
            parentColumns = ["id"],
            childColumns = ["avaliacaoId"],
            onDelete = ForeignKey.CASCADE
        )
        ]
)
data class EstacionamentoAvaliacaoCrossRef(
    val estacionamentoId: Long,
    val avaliacaoId: Long
)
