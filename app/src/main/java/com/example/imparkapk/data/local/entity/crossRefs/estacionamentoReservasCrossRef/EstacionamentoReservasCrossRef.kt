package com.example.imparkapk.data.local.entity.crossRefs.estacionamentoReservasCrossRef

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "estacionamento_reservas_cross_ref",
    primaryKeys = ["estacionamentoId", "reservaId"],
    foreignKeys = [
        ForeignKey(
            entity = com.example.imparkapk.data.local.entity.EstacionamentoEntity::class,
            parentColumns = ["id"],
            childColumns = ["estacionamentoId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = com.example.imparkapk.data.local.entity.ReservaEntity::class,
            parentColumns = ["id"],
            childColumns = ["reservaId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class EstacionamentoReservasCrossRef(
    val estacionamentoId: Long,
    val reservaId: Long
)
