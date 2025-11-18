package com.example.imparkapk.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.imparkapk.data.local.entity.EstacionamentoEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface EstacionamentoDao {
    @Transaction
    @Query("SELECT * FROM estacionamento WHERE ativo = 1")
    fun observerAll(): Flow<List<EstacionamentoEntity>>

    @Transaction
    @Query("SELECT * FROM estacionamento WHERE id = :id")
    fun observeById(id: Long?): Flow<EstacionamentoEntity>

    @Transaction
    @Query("SELECT * FROM estacionamento WHERE ativo = 1")
    suspend fun listAll(): List<EstacionamentoEntity>

    @Transaction
    @Query("SELECT * FROM estacionamento WHERE id = :id")
    suspend fun getById(id: Long): EstacionamentoEntity

    @Transaction
    @Query("SELECT * FROM estacionamento WHERE pending_sync = 1")
    suspend fun getByPending(): List<EstacionamentoEntity>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(estacionamento: EstacionamentoEntity)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(estacionamento: List<EstacionamentoEntity>)

    @Transaction
    @Delete
    suspend fun delete(estacionamento: EstacionamentoEntity)

    @Transaction
    @Delete
    suspend fun deleteById(id: Long)
}