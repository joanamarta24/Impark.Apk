package com.example.imparkapk.data.dao.local.dao.entity.ClienteWithAvaliações

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.example.imparkapk.data.dao.local.dao.entity.AvaliacaoEntity
import com.example.imparktcc.model.Cliente

@Entity(
   tableName = "cliente_avaliacao_ref",
   primaryKeys = ["clienteId","avaliacaoId"],
   foreignKeys =[
       ForeignKey(
           entity = Cliente::class,
           parentColumns = ["id"],
           childColumns = ["clienteId"],
           onDelete = ForeignKey.CASCADE
       ),
       ForeignKey(
           entity = AvaliacaoEntity::class,
           parentColumns = ["id"],
           childColumns = ["avaliacaoId"],
           onDelete = ForeignKey.CASCADE
       )
   ],
    indices = [
        Index(value = ["clienteId"]),
        Index(value = ["avaliacaoId"]),
        Index(value = ["clienteId", "avaliacaoId"], unique = true)
    ]
)
data class ClienteAvaliacaoCrossRef(
    val clienteId: String, // Alterado para String
    val avaliacaoId: String // Alterado para String
)