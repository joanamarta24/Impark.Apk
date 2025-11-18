package com.example.imparkapk.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.imparkapk.data.local.entity.AcessoEntity
import com.example.imparkapk.data.local.entity.AvaliacaoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AvaliacaoDao {

    @Transaction
    @Query("SELECT * FROM avaliacoes WHERE ativo = 1")
    fun observerAll(): Flow<List<AvaliacaoEntity>>

    @Transaction
    @Query("SELECT * FROM avaliacoes WHERE id = :id")
    fun observeById(id: Long): Flow<AvaliacaoEntity>

    @Transaction
    @Query("SELECT * FROM avaliacoes WHERE ativo = 1")
    suspend fun listAll(): List<AvaliacaoEntity>

    @Transaction
    @Query("SELECT * FROM avaliacoes WHERE id = :id")
    suspend fun getById(id: Long): AvaliacaoEntity

    @Transaction
    @Query("SELECT * FROM avaliacoes WHERE pending_sync = 1")
    suspend fun getByPending(): List<AvaliacaoEntity>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(avaliacoes: AvaliacaoEntity)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(cliente: List<AvaliacaoEntity>)

    @Transaction
    @Delete
    suspend fun delete(avaliacoes: AvaliacaoEntity)

    @Transaction
    @Delete
    suspend fun deleteById(id: Long)
}