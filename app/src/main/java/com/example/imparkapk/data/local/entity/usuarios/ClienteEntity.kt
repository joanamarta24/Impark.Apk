package com.example.imparkapk.data.local.entity.usuarios

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.imparkapk.data.local.entity.AvaliacaoEntity
import com.example.imparkapk.data.local.entity.CarroEntity
import com.example.imparkapk.data.local.entity.ReservaEntity
import com.example.imparkapk.domain.model.enuns.TipoDeUsuario
import java.util.Date

@Entity(
    tableName = "cliente",
    foreignKeys = [
        ForeignKey(
            entity = CarroEntity::class,
            parentColumns = ["id"],
            childColumns = ["carros"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ReservaEntity::class,
            parentColumns = ["id"],
            childColumns = ["reservas"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = AvaliacaoEntity::class,
            parentColumns = ["id"],
            childColumns = ["avaliacoes"],
            onDelete = ForeignKey.CASCADE
        )
    ]
    )
data class ClienteEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "nome")
    val nome: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "senha")
    val senha: String?,

    @ColumnInfo(name = "telefone")
    val telefone: String,

    @ColumnInfo(name = "data_nascimento")
    val dataNascimento: Date,

    @ColumnInfo(name = "tipo_usuario")
    val tipoUsuario: TipoDeUsuario = TipoDeUsuario.CLIENTE,

    //TODO(Ponto de relação #5 OneToMany)

    //TODO(Ponto de relação #3 OneToMany)

    @ColumnInfo(name = "ativo")
    val ativo: Boolean,

    @ColumnInfo(name = "updated_at")
    val updatedAt: Long,

    @ColumnInfo(name = "pending_sync")
    val pendingSync: Boolean,

    @ColumnInfo(name = "local_only")
    val localOnly: Boolean = false,

    @ColumnInfo(name = "operation_type")
    val operationType: String? = null
)
