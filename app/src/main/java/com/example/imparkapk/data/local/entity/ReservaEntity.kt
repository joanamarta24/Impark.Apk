package com.example.imparkapk.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.imparkapk.data.local.entity.usuarios.ClienteEntity
import com.example.imparkapk.domain.model.enuns.StatusDeReserva
import java.sql.Time
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

    @ColumnInfo(name = "cliente_id")
    val clienteId: Long?,

    @ColumnInfo(name = "carro_id")
    val carroId: Long?,

    @ColumnInfo(name = "estacionamento_id")
    val estacionamentoId: Long?,

    @ColumnInfo(name = "data_reserva")
    val dataReserva: Date,

    @ColumnInfo(name = "hora_entrada")
    val horaEntrada: Time, // "14:30"

    @ColumnInfo(name = "hora_saida")
    val horaSaida: Time, // "16:30"

    @ColumnInfo(name = "valor_total")
    val valorTotal: Double,

    @ColumnInfo(name = "status")
    val status: StatusDeReserva,

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
