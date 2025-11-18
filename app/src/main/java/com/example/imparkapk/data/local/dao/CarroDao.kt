package com.example.imparkapk.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.imparkapk.data.local.entity.CarroEntity
import com.example.imparkapk.data.local.entity.usuarios.ClienteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CarroDao {
    @Transaction
    @Query("SELECT * FROM carro WHERE ativo = 1")
    fun observerAll(): Flow<List<CarroEntity>>

    @Transaction
    @Query("SELECT * FROM carro WHERE id = :id")
    fun observeById(id: Long): Flow<CarroEntity>

    @Transaction
    @Query("SELECT * FROM carro WHERE ativo = 1")
    suspend fun listAll(): List<CarroEntity>

    @Transaction
    @Query("SELECT * FROM carro WHERE id = :id")
    suspend fun getById(id: Long): CarroEntity

    @Transaction
    @Query("SELECT * FROM carro WHERE pending_sync = 1")
    suspend fun getByPending(): List<CarroEntity>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(carro: CarroEntity)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(carro: List<CarroEntity>)

    @Transaction
    @Delete
    suspend fun delete(carro: CarroEntity)

    @Transaction
    @Query("DELETE FROM carro WHERE id = :id")
    suspend fun deleteById(id: Long)
}