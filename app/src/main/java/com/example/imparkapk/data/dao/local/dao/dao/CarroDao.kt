package com.example.imparkapk.data.dao.local.dao.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.imparkapk.data.dao.entity.CarroEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CarroDao {
    @Query("SELECT * FROM carros WHERE modelo LIKE :modelo AND ativo = 1")
    suspend fun searchCarrosPorModelo(modelo: String): List<CarroEntity>

    @Query("SELECT * FROM carros WHERE cor LIKE :cor AND ativo = 1")
    suspend fun searchCarrosPorCor(cor: String): List<CarroEntity>

    @Query("SELECT * FROM carros WHERE placa = :placa AND ativo = 1")
    suspend fun getCarroPorPlaca(placa: String): CarroEntity?
    @Query("SELECT * FROM carros WHERE id = :id")
    suspend fun getCarroById(id: String): CarroEntity?

    @Query("SELECT * FROM carros WHERE usuario_id = :usuarioId AND ativo = 1")
    fun getCarrosPorUsuario(usuarioId: String): Flow<List<CarroEntity>>

    @Query("SELECT * FROM carros WHERE placa = :placa")
    suspend fun getCarroPorPlaca(placa: String): CarroEntity?

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertCarro(carro: CarroEntity)

    @Update
    suspend fun updateCarro(carro: CarroEntity)

    @Query("DELETE FROM carros WHERE id = :id")
    suspend fun deleteCarro(id: String)

    @Query("UPDATE carros SET ativo = :ativo WHERE id = :id")
    suspend fun updateStatusCarro(id: String, ativo: Boolean)

    @Query("SELECT COUNT(*) FROM carros WHERE usuario_id = :usuarioId AND ativo = 1")
    suspend fun countCarrosPorUsuario(usuarioId: String): Int
}