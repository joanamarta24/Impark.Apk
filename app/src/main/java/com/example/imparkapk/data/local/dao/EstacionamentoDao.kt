package com.example.imparkapk.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.imparkapk.data.local.entity.EstacionamentoEntity
import com.example.imparkapk.data.local.entity.usuarios.ClienteEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface EstacionamentoDao {
 @Query("SELECT * FROM estacionamento WHERE ativo = 1")
 fun observerAll(): Flow<List<EstacionamentoEntity>>

 @Query("SELECT * FROM estacionamento WHERE id = :id")
 fun observeById(id: Long): Flow<EstacionamentoEntity>

 @Query("SELECT * FROM estacionamento WHERE ativo = 1")
 suspend fun listAll(): List<EstacionamentoEntity>

 @Query("SELECT * FROM estacionamento WHERE id = :id")
 suspend fun getById(id: Long): EstacionamentoEntity

 @Query("SELECT * FROM estacionamento WHERE pending_sync = 1")
 suspend fun getByPending(): List<EstacionamentoEntity>

 @Insert(onConflict = OnConflictStrategy.REPLACE)
 suspend fun upsert(estacionamento: EstacionamentoEntity)
 @Insert(onConflict = OnConflictStrategy.REPLACE)
 suspend fun upsertAll(estacionamento: List<EstacionamentoEntity>)

 @Delete
 suspend fun delete(estacionamento: EstacionamentoEntity)

 @Delete
 suspend fun deleteById(id: Long)
}