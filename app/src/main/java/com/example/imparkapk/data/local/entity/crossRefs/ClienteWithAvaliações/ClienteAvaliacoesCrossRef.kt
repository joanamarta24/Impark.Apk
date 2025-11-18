package com.example.imparkapk.data.local.entity.crossRefs.ClienteWithAvaliações

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "cliente_reserva_cross_ref",
    primaryKeys = ["clienteId", "reservaId"],
    foreignKeys = [
        ForeignKey(
            entity = com.example.imparkapk.data.local.entity.usuarios.ClienteEntity::class,
            parentColumns = ["id"],
            childColumns = ["clienteId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = com.example.imparkapk.data.local.entity.AvaliacaoEntity::class,
            parentColumns = ["id"],
            childColumns = ["avaliacaoId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ClienteAvaliacoesCrossRef(
    val clienteId: Long,
    val avaliacaoId: Long
)
