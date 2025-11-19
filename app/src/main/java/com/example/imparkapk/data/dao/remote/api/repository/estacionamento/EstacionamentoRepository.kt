package com.example.imparkapk.data.dao.remote.api.repository.estacionamento

import com.example.imparkapk.data.dao.model.Estacionamento

interface EstacionamentoRepository {

    suspend fun cadastrarEstacionamento(estacionamento: Estacionamento): Result<Boolean>
    suspend fun getEstacionamentoPorId(id: String): Result<Estacionamento?>
    suspend fun listarEstacionamentos(): Result<List<Estacionamento>>
    suspend fun listarEstacionamentosComVagas(): Result<List<Estacionamento>>
    suspend fun atualizarEstacionamento(estacionamento: Estacionamento): Result<Boolean>
    suspend fun atualizarVagasDisponiveis(id: String, vagas: Int): Result<Boolean>

    //BUSCAS E FILTROS
    suspend fun buscarEstacionamentoPorId(id: String): Estacionamento?
    suspend fun buscarEstacionamentoPorNome(nome: String): List<Estacionamento>
    suspend fun buscarEstacionamentoProximos(
        latitude: Double,
        longitude: Double,
        raioKm: Double
    ): List<Estacionamento>
    suspend fun buscarEstacionamentoPorPreco(maxPreco: Double): List<Estacionamento>

    suspend fun buscarEstacionamentoComFiltros(
        nome: String? = null,
        maxPreco: Double? = null,
        latitude: Double? = null,
        longitude: Double? = null,
        raioKm: Double? = null,
        comVagas: Boolean = false
    ): List<Estacionamento>
    //VALIDAÇÕES
    fun validarCoordenadas(latitude: Double,longitude: Double): Boolean
    fun validarHorario(horario: String): Boolean
    fun validarTelefone(telefone: String): Boolean
    fun validarPreco(preco: Double): Boolean

    //METRICAS
    suspend fun getMediaAvaliacoes(estacionamentoId: String): Result<Double>
    suspend fun countEstacionamentos(): Result<Int>
    suspend fun getTaxaOcupacao(estacionamentoId: String): Result<Double>
}
