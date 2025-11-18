package com.example.imparkapk.data.local.entity.crossRefs.estacionamentoWithAvaliacao

import androidx.room.Embedded
import androidx.room.Relation
import com.example.imparkapk.data.local.entity.AvaliacaoEntity
import com.example.imparkapk.data.local.entity.EstacionamentoEntity
import com.example.imparkapk.domain.model.Avaliacao
import com.example.imparkapk.domain.model.Estacionamento

data class EstacionamentoWithAvaliacao(
    @Embedded val estacionamento: EstacionamentoEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = androidx.room.Junction(
            EstacionamentoAvaliacaoCrossRef::class,
            parentColumn = "estacionamentoId",
            entityColumn = "avaliacaoId"
        )
    )
    val avaliacaoIds: List<AvaliacaoEntity>
)