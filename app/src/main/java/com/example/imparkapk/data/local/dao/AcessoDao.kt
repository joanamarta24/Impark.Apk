package com.example.imparkapk.data.local.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.imparkapk.data.local.entity.AcessoEntity
import com.example.imparkapk.data.local.entity.usuarios.ClienteEntity
import kotlinx.coroutines.flow.Flow

interface AcessoDao {
    @Query("SELECT * FROM acesso WHERE ativo = 1")
    fun observerAll(): Flow<List<AcessoEntity>>

    @Query("SELECT * FROM acesso WHERE id = :id")
    fun observeById(id: Long): Flow<AcessoEntity>

    @Query("SELECT * FROM acesso WHERE ativo = 1")
    suspend fun listAll(): List<AcessoEntity>

    @Query("SELECT * FROM acesso WHERE id = :id")
    suspend fun getById(id: Long): AcessoEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(acesso: AcessoEntity)

    @Query("SELECT * FROM acesso WHERE pending_sync = 1")
    suspend fun getByPending(): List<AcessoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(cliente: List<AcessoEntity>)

    @Delete
    suspend fun delete(acesso: AcessoEntity)

    @Delete
    suspend fun deleteById(id: Long)
}