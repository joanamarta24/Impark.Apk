package com.example.imparkapk.data.local.entity.crossRefs.ClienteWithAvaliações

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ClienteWithAvaliacoes(
    @Embedded val clienteId: Long,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            ClienteAvaliacoesCrossRef::class,
            parentColumn = "clienteId",
            entityColumn = "avaliacaoId"
        )
    )
    val avaliacoesIds: List<Long>
)
