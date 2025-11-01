package com.example.imparkapk.data.dao.remote.api.repository

import com.example.imparkapk.data.dao.model.Estacionamento
import kotlinx.coroutines.flow.Flow

interface EstacionamentoRepository {
    suspend fun cadastrarEstacionamento(estacionamento: Estacionamento): Boolean
    suspend fun getEstacionamentoPorId(id: String): Estacionamento?
    suspend fun listarEstacionamentos(): List<Estacionamento>
    suspend fun listarEstacionamentosComVagas(): List<Estacionamento>
    suspend fun atualizarEstacionamento(estacionamento: Estacionamento): Boolean
    suspend fun atualizarVagasDisponiveis(id: String, vagas: Int): Boolean

    //BUSCAS E FILTROS
    suspend fun buscarEstacionamentoPorId(id: String): Estacionamento?
    suspend fun buscarEstacionamentoPorNome(nome: String): List<Estacionamento>
    suspend fun buscarEstacionamentoProximos(latitude: Double,longitude: Double,raioKm: Double): List<Estacionamento>
    suspend fun buscarEstacionamentoPorPreco(maxPreco: Double): List<Estacionamento>

    //VALIDAÇÕES
    fun validarCoordenadas(latitude: Double,longitude: Double): Boolean
    fun validarHorario(horario: String): Boolean
    fun validarTelefone(telefone: String): Boolean
    fun validarPreco(preco: Double): Boolean

    //METRICAS
    suspend fun getMediaAvaliacoes(estacionamentoId: String): Double
    suspend fun countEstacionamentos(): Int
    suspend fun getTaxaOcupacao(estacionamentoId: String): Double
}