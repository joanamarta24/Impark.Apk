package com.example.imparkapk.data.local.dao.usuarios

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Dao
import androidx.room.Transaction
import com.example.imparkapk.data.local.entity.usuarios.ClienteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClienteDao {
    @Transaction
    @Query("SELECT * FROM cliente WHERE ativo = 1")
    fun observerAll(): Flow<List<ClienteEntity>>

    @Transaction
    @Query("SELECT * FROM cliente WHERE id = :id")
    fun observeById(id: Long?): Flow<ClienteEntity?>

    @Transaction
    @Query("SELECT * FROM cliente WHERE ativo = 1")
    suspend fun listAll(): List<ClienteEntity>

    @Transaction
    @Query("SELECT * FROM cliente WHERE id = :id")
    suspend fun getById(id: Long): ClienteEntity?

    @Transaction
    @Query("SELECT * FROM cliente WHERE pending_sync = 1")
    suspend fun getByPending(): List<ClienteEntity>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(cliente: ClienteEntity)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(cliente: List<ClienteEntity>)

    @Transaction
    @Delete
    suspend fun delete(cliente: ClienteEntity)

    @Transaction
    @Delete
    suspend fun deleteById(id: Long)
}