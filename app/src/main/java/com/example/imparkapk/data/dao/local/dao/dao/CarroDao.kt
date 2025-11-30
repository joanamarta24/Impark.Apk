package com.example.imparkapk.data.dao.local.dao.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.imparkapk.data.dao.local.dao.entity.CarroEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CarroDao {
    @Query("SELECT * FROM carros WHERE modelo LIKE :modelo AND ativo = 1")
    suspend fun searchCarrosPorModelo(modelo: String): List<CarroEntity>

    @Query("SELECT * FROM carros WHERE cor LIKE :cor AND ativo = 1")
    suspend fun searchCarrosPorCor(cor: String): List<CarroEntity>

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

    @Query("SELECT *FROM carros WHERE id = id")
    suspend fun buscarPorId(id: String): CarroEntity?

    @Query("SELECT * FROM carros WHERE usuario_id = :usuarioId AND principal = 1 AND ativo = 1 LIMIT 1")
    suspend fun buscarPrincipalPorUsuario(usuarioId: String): CarroEntity?

    @Query("UPDATE carros SET principal = 0 WHERE usuario_id = :usuarioId")
    suspend fun removerPrincipalDeUsuario(usuarioId: String)

    @Query("UPDATE carros SET principal = 1 WHERE id = :carroId")
    suspend fun definirComoPrincipal(carroId: String)

    @Query("SELECT * FROM carros WHERE modelo LIKE :modelo AND ativo = 1")
    suspend fun buscarPorModelo(modelo: String): List<CarroEntity>

    @Query("SELECT * FROM carros WHERE marca LIKE :marca AND ativo = 1")
    suspend fun buscarPorMarca(marca: String): List<CarroEntity>

    @Query("SELECT * FROM carros WHERE cor LIKE :cor AND ativo = 1")
    suspend fun buscarPorCor(cor: String): List<CarroEntity>

    @Query("SELECT * FROM carros WHERE ano = :ano AND ativo = 1")
    suspend fun buscarPorAno(ano: Int): List<CarroEntity>

    @Query("UPDATE carros SET ativo = 0 WHERE id = :id")
    suspend fun marcarComoInativo(id: String)

    @Query("DELETE FROM carros WHERE usuario_id = :usuarioId")
    suspend fun limparPorUsuario(usuarioId: String)

    @Query("DELETE FROM carros")
    suspend fun limparTodos()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(carro: CarroEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirTodos(carros: List<CarroEntity>)

    @Update
    suspend fun atualizar(carro: CarroEntity)

    @Update
    suspend fun atualizarTodos(carros: List<CarroEntity>)
}