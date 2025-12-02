package com.example.imparkapk.data.dao.remote.api.repository.avaliacao

import com.example.imparkapk.data.dao.model.Avaliacao

interface AvaliacaoRepository {
    // Operações básicas de avaliação
    suspend fun criarAvaliacao(avaliacao: Avaliacao): Boolean
    suspend fun getAvaliacaoPorId(id: String): Result<Avaliacao?>
    suspend fun listarAvaliacoesPorEstacionamento(estacionamentoId: String): List<Avaliacao>
    suspend fun listarMinhasAvaliacoes(usuarioId: String): Result<List<Avaliacao>>
    suspend fun atualizarAvaliacao(avaliacao: Avaliacao): Boolean
    suspend fun deletarAvaliacao(avaliacaoId: String): Boolean
    suspend fun sincronizarUsuarios()


    //CONSULTAS E ESTATISTICAS
    suspend fun calcularMediaAvaliacoes(estacionamentoId: String): Result <Double>
    suspend fun countAvaliacoesPorEstacionamento(estacionamentoId: String): Int
    suspend fun countMinhasAvaliacoes(usuarioId: String): Int
    suspend fun getAvaliacoesPorUsuarioEEstacionamento(usuarioId: String, estacionamentoId: String): Result<Avaliacao?>

     //VALIDAÇÕES
    suspend fun verificarPodeAvaliar(usuarioId: String, estacionamentoId: String): Boolean
    fun validarNota(nota: Int): Boolean
    fun validarComentario(comentario: String): Boolean

    //RELATÓRIOS E ANÁLISES
    suspend fun getAvaliacoesRecentes(estacionamentoId: String,limite: Int =5): List<Avaliacao>
    suspend fun getDistribuicaoNotas(estacionamentoId: String): Map<Int, Int>
    suspend fun getAvaliacoesComComentario(estacionamentoId: String): List<Avaliacao>

    //OPERAÇÕES EM LOTE
    suspend fun sincronizarAvaliacoes(usuarioId: String): Result<Boolean>
}