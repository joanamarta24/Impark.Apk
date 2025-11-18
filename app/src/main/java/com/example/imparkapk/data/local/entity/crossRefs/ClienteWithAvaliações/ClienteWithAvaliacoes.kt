package com.example.imparkapk.data.local.entity.crossRefs.ClienteWithAvaliações

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.imparkapk.data.local.entity.AvaliacaoEntity
import com.example.imparkapk.data.local.entity.usuarios.ClienteEntity

data class ClienteWithAvaliacoes(
    @Embedded val clienteId: ClienteEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            ClienteAvaliacoesCrossRef::class,
            parentColumn = "clienteId",
            entityColumn = "avaliacaoId"
        )
    )
    val avaliacoesIds: List<AvaliacaoEntity>
)
