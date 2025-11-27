package com.example.imparkapk.data.dao.remote.api.repository.gerente

import com.example.imparkapk.data.dao.model.Gerente
import com.example.imparkapk.data.dao.model.enus.NivelAcesso

interface GerenteRepository {
    //OPERAÇÕES BÁSICAS DE GERENTE
    suspend fun cadastrarGerente(gerente: Gerente): Boolean
    suspend fun getGerente(id: String): Result<Gerente?>
    suspend fun getGerentePorUsuarioEEstacionamento(usuarioId: String,estacionamentoId: String): Result<Gerente?>
    suspend fun listarGerentesPorEstacionamento(estacionamentoId: String): List<Gerente>
    suspend fun listarGerentesPorUsuario(usuarioId: String): List<Gerente>
    suspend fun atualizarGerente(gerente: Gerente): Boolean
    suspend fun deletarGerente(gerenteId: String): Boolean

    //GESTÃO DE ACESSO
    suspend fun atualizarNivelAcesso(gerenteId: String,novoNivel: Int): Boolean
    suspend fun ativarGerente(gerenteId: String): Boolean
    suspend fun desativarGerente(gerenteId: String): Boolean

    //VALIDAÇÕES E VERIFICAÇÕES
    suspend fun verificarPermissaoGerente(usuarioId: String,estacionamentoId: String,acao: String): Boolean
    suspend fun isGerentePrincipal(gerenteId: String): Boolean
    suspend fun isSupervisor(gerenteId: String): Boolean
    suspend fun isFuncionario(gerenteId: String): Boolean

    //ESTATISTICAS E RELÁTORIOS
    suspend fun countGerentesPorEstacionamento(estacionamentoId: String): Int
    suspend fun countGerentesPorNivel(estacionamentoId: String, nivel: Int): Int
    suspend fun getHierarquiaEstacionamento(estacionamentoId: String): Map<Int, List<Gerente>>

    // Permissões e funções
    suspend fun getPermissoesPorNivel(nivel: Int): List<String>
    suspend fun atualizarPermissoesGerente(gerenteId: String, permissoes: List<String>): Boolean

    suspend fun getNivelAcesso(gerenteId: String,nivelAcesso: List<String>): Boolean
}

