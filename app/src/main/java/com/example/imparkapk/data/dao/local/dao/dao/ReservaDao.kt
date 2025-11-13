package com.example.imparkapk.data.dao.local.dao.dao

import androidx.room.*
import com.example.imparkapk.data.dao.entity.ReservaEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface ReservaDao {

    @Query("SELECT * FROM reservas WHERE id = :id")
    suspend fun getReservaById(id: String): ReservaEntity?

    @Query("SELECT * FROM reservas WHERE usuario_id = :usuarioId ORDER BY data_reserva DESC")
    fun getReservasPorUsuario(usuarioId: String): Flow<List<ReservaEntity>>

    @Query("SELECT * FROM reservas WHERE estacionamento_id = :estacionamentoId ORDER BY data_reserva DESC")
    fun getReservasPorEstacionamento(estacionamentoId: String): Flow<List<ReservaEntity>>

    @Query("SELECT * FROM reservas WHERE status = :status AND usuario_id = :usuarioId")
    fun getReservasPorStatus(usuarioId: String, status: String): Flow<List<ReservaEntity>>

    @Query("SELECT * FROM reservas WHERE data_reserva = :data AND estacionamento_id = :estacionamentoId")
    suspend fun getReservasPorDataEstacionamento(data: Date, estacionamentoId: String): List<ReservaEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReserva(reserva: ReservaEntity)

    @Update
    suspend fun updateReserva(reserva: ReservaEntity)

    @Query("UPDATE reservas SET status = :status WHERE id = :id")
    suspend fun updateStatusReserva(id: String, status: String)

    @Query("DELETE FROM reservas WHERE id = :id")
    suspend fun deleteReserva(id: String)
}