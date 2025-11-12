package com.example.imparkapk.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "acesso",
    foreignKeys = [
        ForeignKey(
            entity = EstacionamentoEntity::class,
            parentColumns = ["id"],
            childColumns = ["estacionamento_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CarroEntity::class,
            parentColumns = ["id"],
            childColumns = ["carro_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class AcessoEntity(
    @PrimaryKey
    val id: Long,

    @ColumnInfo(name = "estacionamento_id")
    val estacionamentoId: Long?,

    @ColumnInfo(name = "carro_id")
    val carroId: Long?,

    @ColumnInfo(name = "placa")
    val placa: String,

    @ColumnInfo(name = "hora_de_entrada")
    val horaDeEntrada: Date,

    @ColumnInfo(name = "hora_de_saida")
    val horaDeSaida: Date,

    @ColumnInfo(name = "horas_totais")
    val horasTotais: Int,

    @ColumnInfo(name = "valor_total")
    val valorTotal: Double,

    @ColumnInfo(name = "ativo")
    val ativo: Boolean
)
