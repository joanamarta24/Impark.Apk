package com.example.imparkapk.data.dao.local.dao.entity.ClienteWithAvaliações

import androidx.annotation.RequiresPermission
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.imparkapk.data.dao.local.dao.entity.AvaliacaoEntity
import com.example.imparkapk.data.dao.local.dao.entity.ClienteEntity

data class ClienteAvaliacoesCrossRef(
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