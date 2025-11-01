package com.example.imparkapk.data.dao.remote.api.repository

import com.example.imparkapk.data.dao.model.Avaliacao
import kotlinx.coroutines.flow.Flow

interface AvaliacaoRepository {
    suspend fun criarAvaliacao(avaliacao: Avaliacao): Result<Boolean>
    fun getAvaliacoesPorEstacionamento(estacionamentoId: String): Flow<List<Avaliacao>>
    suspend fun getMediaAvaliacoes(estacionamentoId: String): Result<Double>
}