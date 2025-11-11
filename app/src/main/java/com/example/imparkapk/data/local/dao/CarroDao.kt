package com.example.imparkapk.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.imparkapk.data.local.entity.CarroEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CarroDao {
    @Query("SELECT * FROM carro WHERE ativo = 1")
    fun observerAll(): Flow<List<CarroEntity>>

    @Query("SELECT * FROM carro WHERE id = :id")
    fun observeById(id: Long): Flow<CarroEntity>

    @Query("SELECT * FROM carro WHERE ativo = 1")
    suspend fun listAll(): List<CarroEntity>

    @Query("SELECT * FROM carro WHERE id = :id")
    suspend fun getById(id: Long): CarroEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(carro: CarroEntity)

    @Delete
    suspend fun delete(carro: CarroEntity)

    @Delete
    suspend fun deleteById(id: Long)
}