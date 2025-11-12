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
            childColumns = ["usuario_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CarroEntity(
    @PrimaryKey
    val id: Long,

    @ColumnInfo(name = "usuario_id")
    val usuarioId: Long?,

    @ColumnInfo(name = "modelo")
    val modelo: String,

    @ColumnInfo (name = "placa")
    val placa: String,

    @ColumnInfo(name = "ativo")
    val ativo: Boolean = true

)
