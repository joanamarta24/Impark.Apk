package com.example.imparkapk.data.dao.local.dao.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "gerentes",
    foreignKeys = [
        ForeignKey(
            entity = EstacionamentoEntity::class,
            parentColumns = ["id"],
            childColumns = ["estacionamento_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["email"], unique = true),
        Index(value = ["cpf"], unique = true),
        Index(value = ["estacionamento_id"])
    ],

)
data class GerenteEntity(
    @PrimaryKey val id: String,
    val nome: String,
    val email: String,
    val senha: String,
    val cpf: String,
    val telefone: String,
    val usuarioId: String? = null,
    val estacionamentoId: String,
    val nivelAcesso: Int = 1,
    val ativo: Boolean = true,
    val dataCriacao: Date = Date(),
    val dataAtualizacao: Date = Date()
)
