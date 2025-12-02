package com.example.imparkapk.data.dao.remote.api.repository.veiculo

import com.example.imparkapk.data.dao.model.Veiculo
import com.example.imparkapk.data.dao.model.VeiculoEstatisticas
import com.example.imparkapk.data.dao.remote.api.response.AcessoResponse.VeiculoEstatistica
import kotlinx.coroutines.flow.Flow

interface VeiculoRepository {

    // ========== OPERAÇÕES CRUD BÁSICAS ==========

    suspend fun criarVeiculo(veiculo: Veiculo): Result<Veiculo>

    suspend fun obterVeiculoPorId(id: String): Result<Veiculo?>

    suspend fun atualizarVeiculo(veiculo: Veiculo): Result<Boolean>

    suspend fun deletarVeiculo(id: String): Result<Boolean>

    // ========== CONSULTAS POR USUÁRIO ==========

    suspend fun obterVeiculosPorUsuario(usuarioId: String): Result<List<Veiculo>>

    fun observarVeiculosPorUsuario(usuarioId: String): Flow<List<Veiculo>>

    suspend fun obterVeiculosAtivosPorUsuario(usuarioId: String): Result<List<Veiculo>>

    fun observarVeiculosAtivosPorUsuario(usuarioId: String): Flow<List<Veiculo>>

    // ========== CONSULTAS POR ESTACIONAMENTO ==========

    suspend fun obterVeiculosPorEstacionamento(estacionamentoId: String): Result<List<Veiculo>>

    suspend fun obterVeiculosAtivosNoEstacionamento(estacionamentoId: String): Result<List<Veiculo>>

    suspend fun obterVeiculosFrequentesNoEstacionamento(estacionamentoId: String, limit: Int = 10): Result<List<Veiculo>>

    // ========== CONSULTAS POR PLACA ==========

    suspend fun obterVeiculoPorPlaca(placa: String): Result<Veiculo?>

    suspend fun buscarVeiculosPorPlaca(placa: String): Result<List<Veiculo>>

    suspend fun verificarPlacaExistente(placa: String): Result<Boolean>

    suspend fun verificarPlacaExistenteParaUsuario(placa: String, usuarioId: String): Result<Boolean>

    // ========== CONSULTAS POR TIPO/MODELO/MARCA ==========

    suspend fun obterVeiculosPorTipo(tipo: String): Result<List<Veiculo>>

    suspend fun obterVeiculosPorMarca(marca: String): Result<List<Veiculo>>

    suspend fun buscarVeiculosPorModelo(modelo: String): Result<List<Veiculo>>

    suspend fun obterVeiculosPorCor(cor: String): Result<List<Veiculo>>

    // ========== CONSULTAS DE BUSCA ==========

    suspend fun buscarVeiculos(termo: String): Result<List<Veiculo>>

    // ========== OPERAÇÕES DE STATUS ==========

    suspend fun ativarVeiculo(id: String): Result<Boolean>

    suspend fun desativarVeiculo(id: String): Result<Boolean>

    suspend fun verificarVeiculoAtivo(id: String): Result<Boolean>

    // ========== CONSULTAS ESTATÍSTICAS ==========

    suspend fun contarVeiculosPorUsuario(usuarioId: String): Result<Int>

    suspend fun contarVeiculosAtivosPorUsuario(usuarioId: String): Result<Int>

    suspend fun contarTotalVeiculos(): Result<Int>

    suspend fun contarVeiculosAtivos(): Result<Int>

    suspend fun obterEstatisticasPorTipo(): Result<Map<String, Int>>

    suspend fun obterTopMarcas(limit: Int = 10): Result<Map<String, Int>>

    // ========== CONSULTAS COM ESTATÍSTICAS AVANÇADAS ==========

    suspend fun obterVeiculosComEstatisticasPorUsuario(usuarioId: String): Result<List<VeiculoEstatisticas>>

    suspend fun obterVeiculoComEstatisticas(id: String): Result<VeiculoEstatisticas?>

    suspend fun obterNovosVeiculosPorMes(meses: Int = 12): Result<Map<String, Int>>

    // ========== VALIDAÇÕES ==========

    suspend fun validarPlaca(placa: String): Result<Boolean>

    suspend fun validarVeiculo(veiculo: Veiculo): Result<Boolean>

    // ========== OPERAÇÕES EM LOTE ==========

    suspend fun criarVeiculosEmLote(veiculos: List<Veiculo>): Result<List<Veiculo>>

    suspend fun atualizarVeiculosEmLote(veiculos: List<Veiculo>): Result<Boolean>

    // ========== SINCRONIZAÇÃO ==========

    suspend fun sincronizarVeiculos(): Result<Boolean>

    suspend fun obterVeiculosNaoSincronizados(): Result<List<Veiculo>>

    suspend fun marcarComoComoSincronizado(id: String): Result<Boolean>

    // ========== BACKUP/RESTAURAÇÃO ==========

    suspend fun criarBackupVeiculos(): Result<String>

    suspend fun restaurarVeiculos(backupData: String): Result<Boolean>
}