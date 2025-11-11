package com.example.imparkapk.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.imparkapk.data.local.entity.AvaliacaoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AvaliacaoDao {

    @Query("SELECT * FROM avaliacoes WHERE ativo = 1")
    fun observerAll(): Flow<List<AvaliacaoEntity>>

    @Query("SELECT * FROM avaliacoes WHERE id = :id")
    fun observeById(id: Long): Flow<AvaliacaoEntity>

    @Query("SELECT * FROM avaliacoes WHERE ativo = 1")
    suspend fun listAll(): List<AvaliacaoEntity>

    @Query("SELECT * FROM avaliacoes WHERE id = :id")
    suspend fun getById(id: Long): AvaliacaoEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(avaliacoes: AvaliacaoEntity)

    @Delete
    suspend fun delete(avaliacoes: AvaliacaoEntity)

    @Delete
    suspend fun deleteById(id: Long)
}