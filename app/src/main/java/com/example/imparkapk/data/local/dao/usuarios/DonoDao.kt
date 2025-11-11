package com.example.imparkapk.data.local.dao.usuarios

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.imparkapk.data.local.entity.usuarios.DonoEntity
import kotlinx.coroutines.flow.Flow

interface DonoDao {

    @Query("SELECT * FROM dono WHERE ativo = 1")
    fun observerAll(): Flow<List<DonoEntity>>

    @Query("SELECT * FROM dono WHERE id = :id")
    fun observeById(id: Long): Flow<DonoEntity>

    @Query("SELECT * FROM dono WHERE ativo = 1")
    suspend fun listAll(): List<DonoEntity>

    @Query("SELECT * FROM dono WHERE id = :id")
    suspend fun getById(id: Long): DonoEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(dono: DonoEntity)

    @Delete
    suspend fun delete(dono: DonoEntity)

    @Delete
    suspend fun deleteById(id: Long)
}