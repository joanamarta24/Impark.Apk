package com.example.imparkapk.data.local.dao.usuarios

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.imparkapk.data.local.entity.usuarios.GerenteEntity
import kotlinx.coroutines.flow.Flow

@Dao

interface GerenteDao {

    @Query("SELECT * FROM gerente WHERE ativo = 1")
    fun observerAll(): Flow<List<GerenteEntity>>

    @Query("SELECT * FROM gerente WHERE id = :id")
    fun observeById(id: Long): Flow<GerenteEntity>

    @Query("SELECT * FROM gerente WHERE ativo = 1")
    suspend fun listAll(): List<GerenteEntity>

    @Query("SELECT * FROM gerente WHERE id = :id")
    suspend fun getById(id: Long): GerenteEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(gerente: GerenteEntity)

    @Delete
    suspend fun delete(gerente: GerenteEntity)

    @Delete
    suspend fun deleteById(id: Long)
}