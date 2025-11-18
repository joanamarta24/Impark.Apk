package com.example.imparkapk.data.local.entity.crossRefs.DonoWithEstacionamento

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "dono_estacionamento_cross_ref",
    primaryKeys = ["donoId", "estacionamentoId"],
    foreignKeys = [
        ForeignKey(
            entity = com.example.imparkapk.data.local.entity.usuarios.DonoEntity::class,
            parentColumns = ["id"],
            childColumns = ["donoId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = com.example.imparkapk.data.local.entity.EstacionamentoEntity::class,
            parentColumns = ["id"],
            childColumns = ["estacionamentoId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DonoEstacionamentoCrossRef (
    val donoId: Long,
    val estacionamentoId: Long
)