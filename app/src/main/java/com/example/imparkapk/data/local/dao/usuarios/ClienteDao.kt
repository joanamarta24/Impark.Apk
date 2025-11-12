package com.example.imparkapk.data.local.dao.usuarios

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.imparkapk.data.local.entity.usuarios.ClienteEntity
import kotlinx.coroutines.flow.Flow

interface ClienteDao {
    @Query("SELECT * FROM cliente WHERE ativo = 1")
    fun observerAll(): Flow<List<ClienteEntity>>

    @Query("SELECT * FROM cliente WHERE id = :id")
    fun observeById(id: Long): Flow<ClienteEntity?>

    @Query("SELECT * FROM cliente WHERE ativo = 1")
    suspend fun listAll(): List<ClienteEntity>

    @Query("SELECT * FROM cliente WHERE id = :id")
    suspend fun getById(id: Long): ClienteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(cliente: ClienteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(cliente: List<ClienteEntity>)

    @Delete
    suspend fun delete(cliente: ClienteEntity)

    @Delete
    suspend fun deleteById(id: Long)
}