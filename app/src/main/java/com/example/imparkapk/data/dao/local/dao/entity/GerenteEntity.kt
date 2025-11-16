package com.example.imparkapk.data.dao.local.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

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
            foreignKeys = [
        ForeignKey(
            entity = ClienteEntity::class, // Ou ClienteEntity, dependendo da sua estrutura
            parentColumns = ["id"],
            childColumns = ["usuario_id"],
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
data class GerenteEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "usuario_id")
    val usuarioId: String,

    @ColumnInfo( "nome_completo")
    val nomeCompleto: String,

    @ColumnInfo( "email")
    val email: String,

    @ColumnInfo("cpf")
    val cpf: String,

    @ColumnInfo("telefone")
    val telefone: String,

    @ColumnInfo("estacionamento_id")
    val estacionamentoId: String,

    @ColumnInfo("nivel_acesso")
    val nivelDeAcesso: Int = 1,

    @ColumnInfo("cargo")
    val cargo: String = "Gerente",

    @ColumnInfo("ativo")
    val ativo: Boolean = true,
    @ColumnInfo(name = "data_criacao")
    val dataCriacao: Date = Date(),

    @ColumnInfo(name = "data_atualizacao")
    val dataAtualizacao: Date = Date(),

    @ColumnInfo(name = "ultimo_login")
    val ultimoLogin: Date? = null


)