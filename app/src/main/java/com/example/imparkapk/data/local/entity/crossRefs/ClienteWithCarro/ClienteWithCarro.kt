package com.example.imparkapk.data.local.entity.crossRefs.ClienteWithCarro

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.imparkapk.data.local.entity.CarroEntity
import com.example.imparkapk.data.local.entity.usuarios.ClienteEntity
import com.example.imparkapk.domain.model.Carro
import com.example.imparkapk.domain.model.usuarios.Cliente

data class ClienteWithCarro(
    @Embedded val clienteId: ClienteEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            ClienteCarroCrossRef::class,
            parentColumn = "clienteId",
            entityColumn = "carroId"
        )
    )
    val carros: List<CarroEntity>
)
