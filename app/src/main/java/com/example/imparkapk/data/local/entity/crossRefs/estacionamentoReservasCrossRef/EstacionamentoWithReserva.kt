package com.example.imparkapk.data.local.entity.crossRefs.estacionamentoReservasCrossRef

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.imparkapk.data.local.entity.EstacionamentoEntity
import com.example.imparkapk.data.local.entity.ReservaEntity

data class EstacionamentoWithReserva (
    @Embedded val estacionamento: EstacionamentoEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            EstacionamentoReservasCrossRef::class,
            parentColumn = "estacionamentoId",
            entityColumn = "reservaId"
        )
    )
    val reservas: List<ReservaEntity>
)