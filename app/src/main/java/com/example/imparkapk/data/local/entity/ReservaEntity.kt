package com.example.imparkapk.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.imparkapk.data.local.entity.usuarios.ClienteEntity
import java.util.Date

@Entity(
    tableName = "reserva",
    foreignKeys = [
        ForeignKey(
            entity = ClienteEntity::class,
            parentColumns = ["id"],
            childColumns = ["cliente_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ClienteEntity::class,
            parentColumns = ["id"],
            childColumns = ["carro_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = EstacionamentoEntity::class,
            parentColumns = ["id"],
            childColumns = ["estacionamento_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ReservaEntity(
    @PrimaryKey
    val id: Long,

    @ColumnInfo(name = "usuario_id")
    val usuarioId: String,

    @ColumnInfo(name = "carro_id")
    val carroId: String,

    @ColumnInfo(name = "estacionamento_id")
    val estacionamentoId: String,

    @ColumnInfo(name = "data_reserva")
    val dataReserva: Date,

    @ColumnInfo(name = "hora_entrada")
    val horaEntrada: String, // "14:30"

    @ColumnInfo(name = "hora_saida")
    val horaSaida: String, // "16:30"

    @ColumnInfo(name = "valor_total")
    val valorTotal: Double,

    @ColumnInfo(name = "status")
    val status: String,

    @ColumnInfo(name = "ativo")
    val ativo: Boolean = true
)
