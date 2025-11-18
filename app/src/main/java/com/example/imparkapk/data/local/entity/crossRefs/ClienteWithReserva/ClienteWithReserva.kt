package com.example.imparkapk.data.local.entity.crossRefs.ClienteWithReserva

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.imparkapk.data.local.entity.ReservaEntity
import com.example.imparkapk.data.local.entity.usuarios.ClienteEntity
import com.example.imparkapk.domain.model.Reserva
import com.example.imparkapk.domain.model.usuarios.Cliente

data class ClienteWithReserva(
    @Embedded val clienteId: ClienteEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            ClienteReservaCrossRef::class,
            parentColumn = "clienteId",
            entityColumn = "reservaId"
        )
    )
    val reservasIds: List<ReservaEntity>
)
