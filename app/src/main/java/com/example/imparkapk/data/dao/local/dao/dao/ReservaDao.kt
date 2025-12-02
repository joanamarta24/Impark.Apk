package com.example.imparkapk.data.dao.local.dao.dao

import androidx.room.*
import com.example.imparkapk.data.dao.local.dao.entity.ReservaEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface ReservaDao {

    @Query("SELECT * FROM reservas WHERE id = :id")
    suspend fun getReservaById(id: String): ReservaEntity?

    @Query("SELECT * FROM reservas WHERE estacionamentoId = :usuarioId ORDER BY datareserva DESC")
    fun getReservasPorUsuario(usuarioId: String): Flow<List<ReservaEntity>>

    @Query("SELECT * FROM reservas WHERE estacionamentoId = :estacionamentoId ORDER BY datareserva DESC")
    fun getReservasPorEstacionamento(estacionamentoId: String): Flow<List<ReservaEntity>>

    @Query("SELECT * FROM reservas WHERE status = :status AND usuarioId = :usuarioId")
    fun getReservasPorStatus(usuarioId: String, status: String): Flow<List<ReservaEntity>>

    @Query("SELECT * FROM reservas WHERE date(datareserva) = date(:data) AND estacionamentoId = :estacionamentoId")
    suspend fun getReservasPorDataEstacionamento(data: Date, estacionamentoId: String): List<ReservaEntity>

    @Query("SELECT * FROM reservas")
    fun getAllReservas(): Flow<List<ReservaEntity>>

    @Query("SELECT * FROM reservas")
    suspend fun getAllReservasSync(): List<ReservaEntity>

    @Query("SELECT * FROM reservas WHERE usuarioId = :usuarioId AND status IN (:statusList)")
    fun getReservasPorStatusList(usuarioId: String, statusList: List<String>): Flow<List<ReservaEntity>>

    @Query("SELECT COUNT(*) FROM reservas WHERE usuarioId = :usuarioId")
    suspend fun countReservasPorUsuario(usuarioId: String): Int

    @Query("SELECT * FROM reservas WHERE usuarioId= :usuarioId AND datareserva >= :dataInicio AND datareserva <= :dataFim")
    fun getReservasPorPeriodo(usuarioId: String, dataInicio: Date, dataFim: Date): Flow<List<ReservaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReserva(reserva: ReservaEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReservasEmLote(reservas: List<ReservaEntity>)

    @Update
    suspend fun updateReserva(reserva: ReservaEntity)

    @Query("UPDATE reservas SET status = :status WHERE id = :id")
    suspend fun updateStatusReserva(id: String, status: String)

    @Query("UPDATE reservas SET status = :status, dataatualizacao = :dataAtualizacao WHERE id = :id")
    suspend fun updateStatusReservaComData(id: String, status: String, dataAtualizacao: Date)

    @Query("DELETE FROM reservas WHERE id = :id")
    suspend fun deleteReserva(id: String)

    @Query("DELETE FROM reservas WHERE usuarioId = :usuarioId")
    suspend fun deleteReservasPorUsuario(usuarioId: String)

    @Query("DELETE FROM reservas")
    suspend fun deleteAllReservas()

    // Métodos para estatísticas
    @Query("SELECT COUNT(*) FROM reservas WHERE usuarioId = :usuarioId AND status = :status")
    suspend fun countReservasPorStatus(usuarioId: String, status: String): Int

    @Query("SELECT SUM(valortotal) FROM reservas WHERE usuarioId = :usuarioId AND status = 'concluida'")
    suspend fun getTotalGastoReservas(usuarioId: String): Double?

    @Query("SELECT AVG(valortotal) FROM reservas WHERE usuarioId = :usuarioId AND status = 'concluida'")
    suspend fun getMediaValorReservas(usuarioId: String): Double?

    // Métodos para verificação de disponibilidade
    @Query("""
        SELECT COUNT(*) FROM reservas 
        WHERE estacionamentoId = :estacionamentoId 
        AND date(datareserva) = date(:data)
        AND status IN ('pendente', 'confirmada', 'ativa')
        AND (
            (horaentrada < :horaSaida AND horasaida > :horaEntrada)
        )
    """)
    suspend fun verificarConflitoHorario(
        estacionamentoId: String,
        data: Date,
        horaEntrada: String,
        horaSaida: String
    ): Int

    // Métodos para busca avançada
    @Query("""
        SELECT * FROM reservas 
        WHERE usuarioId = :usuarioId 
        AND (:status IS NULL OR status = :status)
        AND (:estacionamentoId IS NULL OR estacionamentoId = :estacionamentoId)
        AND (:dataInicio IS NULL OR datareserva >= :dataInicio)
        AND (:dataFim IS NULL OR datareserva <= :dataFim)
        ORDER BY datareserva DESC
    """)
    fun searchReservas(
        usuarioId: String,
        status: String? = null,
        estacionamentoId: String? = null,
        dataInicio: Date? = null,
        dataFim: Date? = null
    ): Flow<List<ReservaEntity>>
}