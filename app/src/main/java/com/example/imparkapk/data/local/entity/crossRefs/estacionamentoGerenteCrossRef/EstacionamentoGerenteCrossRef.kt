package com.example.imparkapk.data.local.entity.crossRefs.estacionamentoGerenteCrossRef

import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.imparkapk.data.local.entity.CarroEntity
import com.example.imparkapk.data.local.entity.EstacionamentoEntity
import com.example.imparkapk.data.local.entity.usuarios.GerenteEntity

@Entity(
    tableName = "estacionamento_gerente_cross_ref",
    primaryKeys = ["estacionamentoId", "gerenteId"],
    foreignKeys = [
        ForeignKey(
            entity = EstacionamentoEntity::class,
            parentColumns = ["id"],
            childColumns = ["estacionamentoId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = GerenteEntity::class,
            parentColumns = ["id"],
            childColumns = ["gerenteId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class EstacionamentoGerenteCrossRef(
    val estacionamentoId: Long,
    val gerenteId: Long
)
