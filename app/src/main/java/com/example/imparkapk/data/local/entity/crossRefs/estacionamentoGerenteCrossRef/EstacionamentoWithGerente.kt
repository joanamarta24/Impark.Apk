package com.example.imparkapk.data.local.entity.crossRefs.estacionamentoGerenteCrossRef

import androidx.room.Embedded
import androidx.room.Relation
import com.example.imparkapk.data.local.entity.usuarios.GerenteEntity
import com.example.imparkapk.domain.model.Estacionamento

data class EstacionamentoWithGerente(
    @Embedded val estacionamento: Estacionamento,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = androidx.room.Junction(
            EstacionamentoGerenteCrossRef::class,
            parentColumn = "estacionamentoId",
            entityColumn = "gerenteId"
        )
    )
    val gerenteIds: List<GerenteEntity>
)
