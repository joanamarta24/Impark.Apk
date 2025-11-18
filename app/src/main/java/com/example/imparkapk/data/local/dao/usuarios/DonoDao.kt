package com.example.imparkapk.data.local.dao.usuarios

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Dao
import com.example.imparkapk.data.local.entity.usuarios.ClienteEntity
import com.example.imparkapk.data.local.entity.usuarios.DonoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DonoDao {

    @Transaction
    @Query("SELECT * FROM dono WHERE ativo = 1")
    fun observerAll(): Flow<List<DonoEntity>>

    @Transaction
    @Query("SELECT * FROM dono WHERE id = :id")
    fun observeById(id: Long): Flow<DonoEntity>

    @Transaction
    @Query("SELECT * FROM dono WHERE ativo = 1")
    suspend fun listAll(): List<DonoEntity>

    @Query("SELECT * FROM dono WHERE id = :id")
    suspend fun getById(id: Long): DonoEntity
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(dono: DonoEntity)
    @Transaction
    @Query("SELECT * FROM dono WHERE pending_sync = 1")
    suspend fun getByPending(): List<DonoEntity>
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(dono: List<DonoEntity>)

    @Transaction
    @Delete
    suspend fun delete(dono: DonoEntity)

    @Transaction
    @Delete
    suspend fun deleteById(id: Long)
}