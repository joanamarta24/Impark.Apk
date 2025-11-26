package com.example.imparkapk.data.dao.local.dao.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.imparkapk.data.dao.local.dao.entity.GerenteEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface GerenteDao {
    @Query("SELECT * FROm gerentes WHERE id = id")
    suspend fun getGrtrnteByid(id: String): GerenteEntity?

    //Buscar por usuário
    @Query("SELECT * FROM gerentes WHERE usuario_id AND ativo = 1")
    suspend fun getGerentePorUsuario(usuarioId: String): GerenteEntity?

    //Buscar por estacionamento com Flow para observação em tempo real
    @Query("SELECT * FROM gerentes WHERE estacionamento_id = estacionamento_id AND ativo =1")
    fun getGerentesPorEstacionamento(estacionamentoId: String): Flow<List<GerenteEntity>>

    // Buscas por estacionamento sem Flow (para operações únicas)
    @Query("SELECT *FROM gerentes WHERE estacionamento_id = estacionamento_id AND ativo = 1")
    suspend fun getGerentesPorEstacionamentoList(estacionamentoId: String): List<GerenteEntity>

    // Buscas por email e CPF
    @Query("SELECT * FROM gerentes WHERE email = email AND ativo = 1")
    suspend fun getGerentePorEmail(email: String): GerenteEntity?


    @Query("SELECT * FROM gerentes WHERE cpf = :cpf AND ativo = 1")
    suspend fun getGerentePorCpf(cpf: String): GerenteEntity?

    // Operações de inserção e atualização
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGerente(gerente: GerenteEntity)

    @Update
    suspend fun updateGerente(gerente: GerenteEntity)

    // Atualização de status
    @Query("UPDATE gerentes SET ativo = :ativo, data_atualizacao = CURRENT_TIMESTAMP WHERE id = :id")
    suspend fun updateStatusGerente(id: String, ativo: Boolean)

    // Contagens e estatísticas
    @Query("SELECT COUNT(*) FROM gerentes WHERE estacionamento_id = :estacionamentoId AND ativo = 1")
    suspend fun countGerentesPorEstacionamento(estacionamentoId: String): Int

    @Query("SELECT COUNT(*) FROM gerentes WHERE estacionamento_id = :estacionamentoId AND nivel_acesso = :nivelAcesso AND ativo = 1")
    suspend fun countGerentesPorNivelAcesso(estacionamentoId: String, nivelAcesso: Int): Int

// Listas observáveis

    @Query("SELECT * FROM gerentes WHERE ativo = 1")
    fun getGerentesAtivos(): Flow<List<GerenteEntity>>

    @Query("SELECT * FROM gerentes WHERE estacionamento_id = :estacionamentoId AND nivel_acesso = :nivelAcesso AND ativo = 1")
    fun getGerentesPorNivelAcesso(
        estacionamentoId: String,
        nivelAcesso: Int
    ): Flow<List<GerenteEntity>>

    // Operações de exclusão
    @Query("DELETE FROM gerentes WHERE id = :id")
    suspend fun deleteGerente(id: String)

    @Query("UPDATE gerentes SET ativo = 0 WHERE id = :id")
    suspend fun softDeleteGerente(id: String)

    // Buscas com filtros combinados
    @Query("SELECT * FROM gerentes WHERE estacionamento_id = :estacionamentoId AND ativo = :ativo")
    fun getGerentesPorEstacionamentoEStatus(
        estacionamentoId: String,
        ativo: Boolean
    ): Flow<List<GerenteEntity>>

    // Verificação de existência
    @Query("SELECT EXISTS(SELECT 1 FROM gerentes WHERE email = :email AND ativo = 1)")
    suspend fun existsByEmail(email: String): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM gerentes WHERE cpf = :cpf AND ativo = 1)")
    suspend fun existsByCpf(cpf: String): Boolean
}