package com.example.imparkapk.data.local.dao.usuarios

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.imparkapk.data.local.entity.usuarios.DonoEntity
import com.example.imparkapk.data.local.entity.usuarios.GerenteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GerenteDao {

    @Transaction
    @Query("SELECT * FROM gerente WHERE ativo = 1")
    fun observerAll(): Flow<List<GerenteEntity>>

    @Transaction
    @Query("SELECT * FROM gerente WHERE id = :id")
    fun observeById(id: Long): Flow<GerenteEntity>

    @Transaction
    @Query("SELECT * FROM gerente WHERE ativo = 1")
    suspend fun listAll(): List<GerenteEntity>

    @Transaction
    @Query("SELECT * FROM gerente WHERE id = :id")
    suspend fun getById(id: Long): GerenteEntity

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(gerente: GerenteEntity)

    @Transaction
    @Query("SELECT * FROM gerente WHERE pending_sync = 1")
    suspend fun getByPending(): List<GerenteEntity>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(gerentes: List<GerenteEntity>)

    @Transaction
    @Delete
    suspend fun delete(gerente: GerenteEntity)

    @Transaction
    @Query("DELETE FROM gerente WHERE id = :id")
    suspend fun deleteById(id: Long)
}