package com.example.imparkapk.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.imparkapk.data.local.entity.ReservaEntity
import com.example.imparkapk.data.local.entity.usuarios.ClienteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservaDao {
    @Transaction
    @Query("SELECT * FROM reserva WHERE ativo = 1")
    fun observerAll(): Flow<List<ReservaEntity>>

    @Transaction
    @Query("SELECT * FROM reserva WHERE id = :id")
    fun observeById(id: Long): Flow<ReservaEntity>

    @Transaction
    @Query("SELECT * FROM reserva WHERE ativo = 1")
    suspend fun listAll(): List<ReservaEntity>

    @Transaction
    @Query("SELECT * FROM reserva WHERE id = :id")
    suspend fun getById(id: Long): ReservaEntity

    @Transaction
    @Query("SELECT * FROM reserva WHERE pending_sync = 1")
    suspend fun getByPending(): List<ReservaEntity>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(reserva: ReservaEntity)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(reserva: List<ReservaEntity>)

    @Transaction
    @Delete
    suspend fun delete(reserva: ReservaEntity)

    @Transaction
    @Query("DELETE FROM reserva WHERE id = :id")
    suspend fun deleteById(id: Long)
}