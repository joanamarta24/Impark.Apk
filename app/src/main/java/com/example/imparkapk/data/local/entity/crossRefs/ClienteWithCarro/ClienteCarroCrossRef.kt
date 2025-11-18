package com.example.imparkapk.data.local.entity.crossRefs.ClienteWithCarro

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "cliente_carro_cross_ref",
    primaryKeys = ["clienteId", "carroId"],
    foreignKeys = [
        ForeignKey(
            entity = com.example.imparkapk.data.local.entity.usuarios.ClienteEntity::class,
            parentColumns = ["id"],
            childColumns = ["clienteId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = com.example.imparkapk.data.local.entity.CarroEntity::class,
            parentColumns = ["id"],
            childColumns = ["carroId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ClienteCarroCrossRef(
    val clienteId: Long,
    val carroId: Long
)
