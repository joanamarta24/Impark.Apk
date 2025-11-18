package com.example.imparkapk.data.local.entity.crossRefs.ClienteWithReserva

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
            entity = com.example.imparkapk.data.local.entity.ReservaEntity::class,
            parentColumns = ["id"],
            childColumns = ["reservaId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ClienteReservaCrossRef(
    val clienteId: Long,
    val reservaId: Long
)
