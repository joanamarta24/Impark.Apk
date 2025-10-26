package com.example.imparkapk.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "reserva",
    foreignKeys = [
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["id"],
            childColumns = ["usuario_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CarroEntity::class,
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
    val id: String,

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
    val status: String, // "pendente", "confirmada", "ativa", "concluida", "cancelada"

    @ColumnInfo(name = "codigo_reserva")
    val codigoReserva: String,

    @ColumnInfo(name = "data_criacao")
    val dataCriacao: Date,

    @ColumnInfo(name = "data_atualizacao")
    val dataAtualizacao: Date
)
