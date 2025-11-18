package com.example.imparkapk.data.local.entity.crossRefs.DonoWithEstacionamento

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.imparkapk.data.local.entity.EstacionamentoEntity
import com.example.imparkapk.data.local.entity.usuarios.DonoEntity

data class DonoWithEstacionamentos (
    @Embedded val dono: DonoEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            DonoWithEstacionamentos::class,
            parentColumn = "donoId",
            entityColumn = "estacionamentoId"
        )
    )
    val estacionamentos: List<EstacionamentoEntity>
)