package com.example.imparkapk.data.dao.local.dao.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.imparkapk.data.dao.entity.GerenteEntity
import kotlinx.coroutines.flow.Flow

@Dao

interface GerenteDao {

    @Query("SELECT * FROM gerentes WHERE id = :id")
    suspend fun getGerenteById(id: String): GerenteEntity?

    @Query("SELECT * FROM gerentes WHERE usuario_id = :usuarioId AND ativo = 1")
    suspend fun getGerentePorUsuario(usuarioId: String): GerenteEntity?

    @Query("SELECT * FROM gerentes WHERE estacionamento_id = :estacionamentoId AND ativo = 1")
    fun getGerentesPorEstacionamento(estacionamentoId: String): Flow<List<GerenteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGerente(gerente: GerenteEntity)

    @Update
    suspend fun updateGerente(gerente: GerenteEntity)

    @Query("UPDATE gerentes SET ativo = :ativo WHERE id = :id")
    suspend fun updateStatusGerente(id: String, ativo: Boolean)

    @Query("SELECT COUNT(*) FROM gerentes WHERE estacionamento_id = :estacionamentoId AND ativo = 1")
    suspend fun countGerentesPorEstacionamento(estacionamentoId: String): Int
}