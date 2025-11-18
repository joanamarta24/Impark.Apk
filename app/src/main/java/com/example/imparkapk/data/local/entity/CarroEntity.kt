package com.example.imparkapk.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.imparkapk.data.local.entity.usuarios.ClienteEntity

@Entity(
    tableName = "carro",
    foreignKeys = [
        ForeignKey(
            entity = ClienteEntity::class,
            parentColumns = ["id"],
            childColumns = ["cliente_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CarroEntity(
    @PrimaryKey
    val id: Long,

    @ColumnInfo(name = "cliente_id")
    val clienteId: Long?,

    @ColumnInfo(name = "modelo")
    val modelo: String,

    @ColumnInfo (name = "placa")
    val placa: String,

    @ColumnInfo(name = "ativo")
    val ativo: Boolean = true,

    @ColumnInfo(name = "updated_at")
    val updatedAt: Long,

    @ColumnInfo(name = "pending_sync")
    val pendingSync: Boolean,

    @ColumnInfo(name = "local_only")
    val localOnly: Boolean = false,

    @ColumnInfo(name = "operation_type")
    val operationType: String? = null

)
