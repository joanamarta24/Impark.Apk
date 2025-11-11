package com.example.imparkapk.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.imparkapk.data.local.entity.ReservaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservaDao {
    @Query("SELECT * FROM reserva WHERE ativo = 1")
    fun observerAll(): Flow<List<ReservaEntity>>

    @Query("SELECT * FROM reserva WHERE id = :id")
    fun observeById(id: Long): Flow<ReservaEntity>

    @Query("SELECT * FROM reserva WHERE ativo = 1")
    suspend fun listAll(): List<ReservaEntity>

    @Query("SELECT * FROM reserva WHERE id = :id")
    suspend fun getById(id: Long): ReservaEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(reserva: ReservaEntity)

    @Delete
    suspend fun delete(reserva: ReservaEntity)

    @Delete
    suspend fun deleteById(id: Long)
}