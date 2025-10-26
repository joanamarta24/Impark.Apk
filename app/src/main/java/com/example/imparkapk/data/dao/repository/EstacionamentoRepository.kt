package com.example.imparkapk.data.dao.repository

import com.example.imparkapk.data.dao.model.Estacionamento
import kotlinx.coroutines.flow.Flow

interface EstacionamentoRepository {
    suspend fun cadastrarEstacionamento(estacionamento: Estacionamento): Result<Boolean>

    suspend fun getEstacionamentoPorId(id: String): Result<Estacionamento?>

    fun getEstacionamentosAtivos(): Flow<List<Estacionamento>>

    fun getEstacionamentosComVagas(): Flow<List<Estacionamento>>

    fun searchEstacionamentos(query: String): Flow <List<Estacionamento>>

    suspend fun atualizarEstacionamento(estacionamento: Estacionamento): Result<Boolean>


}