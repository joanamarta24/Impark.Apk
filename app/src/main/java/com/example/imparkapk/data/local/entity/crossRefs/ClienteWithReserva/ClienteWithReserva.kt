package com.example.imparkapk.data.local.entity.crossRefs.ClienteWithReserva

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ClienteWithReserva(
    @Embedded val clienteId: Long,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            ClienteReservaCrossRef::class,
            parentColumn = "clienteId",
            entityColumn = "reservaId"
        )
    )
    val reservasIds: List<Long>
)
