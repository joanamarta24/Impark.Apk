package com.example.imparkapk.data.local.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Dao
import androidx.room.Transaction
import com.example.imparkapk.data.local.entity.AcessoEntity
import com.example.imparkapk.data.local.entity.usuarios.ClienteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AcessoDao {
    @Transaction
    @Query("SELECT * FROM acesso WHERE ativo = 1")
    fun observerAll(): Flow<List<AcessoEntity>>

    @Transaction
    @Query("SELECT * FROM acesso WHERE id = :id")
    fun observeById(id: Long): Flow<AcessoEntity>

    @Transaction
    @Query("SELECT * FROM acesso WHERE ativo = 1")
    suspend fun listAll(): List<AcessoEntity>

    @Transaction
    @Query("SELECT * FROM acesso WHERE id = :id")
    suspend fun getById(id: Long): AcessoEntity

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(acesso: AcessoEntity)

    @Transaction
    @Query("SELECT * FROM acesso WHERE pending_sync = 1")
    suspend fun getByPending(): List<AcessoEntity>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(cliente: List<AcessoEntity>)

    @Transaction
    @Delete
    suspend fun delete(acesso: AcessoEntity)

    @Transaction
    @Delete
    suspend fun deleteById(id: Long)
}