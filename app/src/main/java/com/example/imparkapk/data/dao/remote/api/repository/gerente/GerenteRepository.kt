package com.example.imparkapk.data.dao.remote.api.repository.gerente

import com.example.imparkapk.data.dao.model.Gerente
import kotlinx.coroutines.flow.Flow

interface GerenteRepository {

    // Operações CRUD básicas
    suspend fun cadastrarGerente(gerente: Gerente): Boolean
    suspend fun getGerente(id: String): Result<Gerente?>
    suspend fun atualizarGerente(gerente: Gerente): Boolean
    suspend fun deletarGerente(gerenteId: String): Boolean

    // Operações de consulta
    suspend fun getGerentePorUsuarioEEstacionamento(usuarioId: String, estacionamentoId: String): Result<Gerente?>
    suspend fun listarGerentesPorEstacionamento(estacionamentoId: String): List<Gerente>
    suspend fun listarGerentesPorUsuario(usuarioId: String): List<Gerente>

    // Operações de nível de acesso
    suspend fun atualizarNivelAcesso(gerenteId: String, novoNivel: Int): Boolean
    suspend fun ativarGerente(gerenteId: String): Boolean
    suspend fun desativarGerente(gerenteId: String): Boolean

    // Operações de permissões
    suspend fun verificarPermissaoGerente(usuarioId: String, estacionamentoId: String, acao: String): Boolean
    suspend fun getPermissoesPorNivel(nivel: Int): List<String>
    suspend fun atualizarPermissoesGerente(gerenteId: String, permissoes: List<String>): Boolean
    suspend fun getNivelAcesso(gerenteId: String, permissoesRequeridas: List<String>): Boolean

    // Operações de verificação de nível
    suspend fun isGerentePrincipal(gerenteId: String): Boolean
    suspend fun isSupervisor(gerenteId: String): Boolean
    suspend fun isFuncionario(gerenteId: String): Boolean

    // Operações de contagem
    suspend fun countGerentesPorEstacionamento(estacionamentoId: String): Int
    suspend fun countGerentesPorNivel(estacionamentoId: String, nivel: Int): Int

    // Operações hierárquicas
    suspend fun getHierarquiaEstacionamento(estacionamentoId: String): Map<Int, List<Gerente>>

    // Métodos adicionais (se necessário)
    suspend fun transferirGerencia(gerenteAtualId: String, novoGerenteId: String): Boolean
    suspend fun promoverGerente(gerenteId: String): Boolean
    suspend fun rebaixarGerente(gerenteId: String): Boolean
    suspend fun getGerentePrincipalEstacionamento(estacionamentoId: String): Gerente?
    suspend fun getSupervisoresEstacionamento(estacionamentoId: String): List<Gerente>
    suspend fun getFuncionariosEstacionamento(estacionamentoId: String): List<Gerente>
    suspend fun verificarSePodeGerenciarEstacionamento(usuarioId: String, estacionamentoId: String): Boolean
    suspend fun getNivelAcessoGerente(gerenteId: String): Int?
}