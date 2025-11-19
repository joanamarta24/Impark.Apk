package com.example.imparkapk.data.dao.local.dao.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.imparkapk.data.dao.local.dao.entity.EstacionamentoEntity
import com.example.imparkapk.data.dao.model.Estacionamento
import kotlinx.coroutines.flow.Flow


@Dao
interface EstacionamentoDao {

   @Query("SELECT * FROM estacionamentos WHERE id= id")
    suspend fun getEstacionamentoById(id: Int): Estacionamento?

    @Query("SELECT* FROM estacionamentos WHERE ativo = 1")
    fun getEstacionamentosAtivos(): Flow<List<EstacionamentoEntity>>

    @Query("SELECT * FROM estacionamentos WHERE vagasDisponiveis > 0 AND ativo = 1")
    fun getEstacionamentosComVagas(): Flow<List<EstacionamentoEntity>>

    @Query("SELECT * FROM estacionamentos WHERE nome LIKE '%'|| :query || '%' || AND ativo = 1")
    fun searchEstacionamentos(query: String): Flow<List<EstacionamentoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEstacionamento(estacionamento: EstacionamentoEntity)

    @Update
    suspend fun updateEstacionamento(estacionamento: EstacionamentoEntity)

    @Query("UPDATE estacionamentos SET vagasDisponiveis= :vagas WHERE id = :id")
    suspend fun updateVagasDisponiveis(id: String, vagas: Int)

    @Query("UPDATE estacionamentos SET ativo = :ativo WHERE id = :id")
    suspend fun updateStatusEstacionamento(id: String, ativo: Boolean)

    @Query("SELECT * FROM estacionamentos WHERE id = id")
    suspend fun buscarPorI(id: String): EstacionamentoEntity?

    @Query("SELECT * FROM estacionamentos WHERE nome LIKE nome AND ativo = 1")
    suspend fun buscarPorNome(nome: String): List<EstacionamentoEntity>

    @Query("SELECT * FROM estacionamentos WHERE ativo = 1")
    suspend fun buscarTodosAtivos(): List<EstacionamentoEntity>

    @Query("SELECT * FROM estacionamentos WHERE valorHora <= :maxPreco AND ativo =1 ORDER BY valor_hora ASC")
    suspend fun buscarPorPrecoMaximo(maxPreco: Double): List<EstacionamentoEntity>

 @Insert(onConflict = OnConflictStrategy.REPLACE)
 suspend fun inserir(estacionamento: EstacionamentoEntity)

 @Insert(onConflict = OnConflictStrategy.REPLACE)
 suspend fun inserirTodos(estacionamentos: List<EstacionamentoEntity>)

 @Query("DELETE FROM estacionamentos WHERE cache_timestamp < :timestamp")
 suspend fun limparEstacionamentosProximos(timestamp: Long = System.currentTimeMillis() - 30*60*100) // 30 minutos


}