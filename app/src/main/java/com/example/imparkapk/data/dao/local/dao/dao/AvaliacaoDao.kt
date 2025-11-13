package com.example.imparkapk.data.dao.local.dao.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.imparkapk.data.dao.entity.AvaliacaoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AvaliacaoDao {

    @Query("SELECT * FROM avaliacoes WHERE id = :id")
    suspend fun getAvaliacaoById(id: String): AvaliacaoEntity?

    @Query("SELECT * FROM avaliacoes WHERE estacionamento_id = :estacionamentoId ORDER BY data_avaliacao DESC")
    fun getAvaliacoesPorEstacionamento(estacionamentoId: String): Flow<List<AvaliacaoEntity>>

    @Query("SELECT * FROM avaliacoes WHERE usuario_id = :usuarioId ORDER BY data_avaliacao DESC")
    fun getAvaliacoesPorUsuario(usuarioId: String): Flow<List<AvaliacaoEntity>>

    @Query("SELECT AVG(nota) FROM avaliacoes WHERE estacionamento_id = :estacionamentoId")
    suspend fun getMediaAvaliacoes(estacionamentoId: String): Double?

    @Query("SELECT COUNT(*) FROM avaliacoes WHERE estacionamento_id = :estacionamentoId")
    suspend fun countAvaliacoes(estacionamentoId: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAvaliacao(avaliacao: AvaliacaoEntity)

    @Update
    suspend fun updateAvaliacao(avaliacao: AvaliacaoEntity)

    @Query("DELETE FROM avaliacoes WHERE id = :id")
    suspend fun deleteAvaliacao(id: String)
}