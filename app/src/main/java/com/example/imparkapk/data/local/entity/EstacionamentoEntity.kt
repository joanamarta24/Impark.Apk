package com.example.imparkapk.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.imparkapk.data.local.entity.usuarios.DonoEntity
import com.example.imparkapk.data.local.entity.usuarios.GerenteEntity
import java.util.Date

@Entity(
    tableName = "estacionamento",
    foreignKeys = [
        ForeignKey(
            entity = DonoEntity::class,
            parentColumns = ["id"],
            childColumns = ["dono_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ReservaEntity::class,
            parentColumns = ["id"],
            childColumns = ["reservas"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = GerenteEntity::class,
            parentColumns = ["id"],
            childColumns = ["gerentes"],
            onDelete = ForeignKey.CASCADE
        ),
    ])
data class EstacionamentoEntity(
    @PrimaryKey
    val id: Long,

    @ColumnInfo(name = "nome")
    val nome: String,

    @ColumnInfo(name = "endereco")
    val endereco: String,

    @ColumnInfo(name = "latitude")
    val latitude: Double,

    @ColumnInfo(name = "longitude")
    val longitude: Double,

    @ColumnInfo(name = "total_vagas")
    val totalVagas: Int,

    @ColumnInfo(name = "vagas_disponiveis")
    val vagasDisponiveis: Int,

    @ColumnInfo(name = "valor_hora")
    val valorHora: Double,

    @ColumnInfo(name = "telefone")
    val telefone: String,

    @ColumnInfo(name = "horario_abertura")
    val horarioAbertura: Date, // "08:00"

    @ColumnInfo(name = "horario_fechamento")
    val horarioFechamento: Date, // "22:00"

    @ColumnInfo(name = "ativo")
    val ativo: Boolean = true,

    @ColumnInfo(name = "vagas_totais")
    val vagasTotal: Int,

    @ColumnInfo(name = "preco_hora")
    val precoHora: Double,

    @ColumnInfo(name = "horario_de_funcionamento")
    val horarioFuncionamento: String,

    @ColumnInfo(name = "dono_id")
    val donoId: Long?,

    //TODO(Ponto de relação #2 OneToMany)

    //TODO(Ponto de relação #3 OneToMany)

    @ColumnInfo(name = "updated_at")
    val updatedAt: Long,

    @ColumnInfo(name = "pending_sync")
    val pendingSync: Boolean,

    @ColumnInfo(name = "local_only")
    val localOnly: Boolean = false,

    @ColumnInfo(name = "operation_type")
    val operationType: String? = null
)
