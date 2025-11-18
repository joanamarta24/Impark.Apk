package com.example.imparkapk.data.local.entity.crossRefs.ClienteWithCarro

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ClienteWithCarro(
    @Embedded val clienteId: Long,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            ClienteCarroCrossRef::class,
            parentColumn = "clienteId",
            entityColumn = "carroId"
        )
    )
    val carros: List<Long>
)
