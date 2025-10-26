package com.example.imparkapk.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(    tableName = "carros",
    foreignKeys = [
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["id"],
            childColumns = ["usuario_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CarroEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "usuario_id")
     val usuarioId: String,

    @ColumnInfo(name = "modelo")
    val modelo: String,

    @ColumnInfo (name = "placa")
    val placa: String,

    @ColumnInfo (name = "cor")
    val cor: String,

    @ColumnInfo(name = "data_criacao")
    val dataCriacao: Date,

    @ColumnInfo(name = "data_atualizacao")
    val dataAtualizacao: Date,

    @ColumnInfo(name = "ativo")
    val ativo: Boolean = true

)
