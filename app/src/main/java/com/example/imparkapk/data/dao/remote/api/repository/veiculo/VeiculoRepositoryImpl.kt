import com.example.imparkapk.data.dao.local.dao.entity.VeiculoEntity
import com.example.imparkapk.data.dao.model.Veiculo
import com.example.imparkapk.data.dao.model.VeiculoEstatisticas
import com.example.imparkapk.data.dao.remote.api.api.VeiculoApi
import com.example.imparkapk.data.dao.remote.api.repository.veiculo.VeiculoRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.Date
import java.util.UUID

@Singleton
class VeiculoRepositoryImpl @Inject constructor(
    private val veiculoDao: VeiculoDao,
    private val veiculoApi: VeiculoApi
) : VeiculoRepository {

    private val veiculosCache = mutableListOf<Veiculo>()

    init {
        inicializarDadosMock()
    }

    private fun inicializarDadosMock() {
        // Dados de demonstração
        veiculosCache.addAll(listOf(
            Veiculo(
                id = "1",
                usuarioId = "1",
                placa = "ABC1234",
                marca = "Toyota",
                modelo = "Corolla",
                anoFabricacao = 2020,
                anoModelo = 2021,
                cor = "Preto",
                tipo = TipoVeiculo.CARRO,
                renavam = "12345678901",
                ativo = true
            ),
            Veiculo(
                id = "2",
                usuarioId = "1",
                placa = "XYZ5678",
                marca = "Honda",
                modelo = "Civic",
                anoFabricacao = 2019,
                anoModelo = 2020,
                cor = "Prata",
                tipo = TipoVeiculo.CARRO,
                ativo = true
            ),
            Veiculo(
                id = "3",
                usuarioId = "2",
                placa = "MNO9012",
                marca = "Yamaha",
                modelo = "Factor 150",
                anoFabricacao = 2021,
                anoModelo = 2022,
                cor = "Vermelho",
                tipo = TipoVeiculo.MOTO,
                ativo = true
            )
        ))
    }

    // ========== OPERAÇÕES CRUD BÁSICAS ==========

    override suspend fun criarVeiculo(veiculo: Veiculo): Result<Veiculo> {
        return withContext(Dispatchers.IO) {
            try {
                // Validações
                val validacao = validarVeiculo(veiculo)
                if (validacao.isFailure || !validacao.getOrDefault(false)) {
                    return@withContext Result.failure(Exception("Veículo inválido"))
                }

                // Verificar se placa já existe
                val placaExistente = verificarPlacaExistente(veiculo.placa)
                if (placaExistente.isSuccess && placaExistente.getOrDefault(false)) {
                    return@withContext Result.failure(Exception("Placa já cadastrada"))
                }

                // Tentar criar na API
                val response = veiculoApi.criarVeiculo(veiculo)
                if (response.isSuccessful && response.body() != null) {
                    val veiculoCriado = response.body()!!

                    // Salvar no banco local
                    val entity = VeiculoEntity.fromModel(veiculoCriado)
                    veiculoDao.inserirVeiculo(entity)

                    // Atualizar cache
                    veiculosCache.add(veiculoCriado)

                    Result.success(veiculoCriado)
                } else {
                    // Fallback: criar localmente
                    val novoVeiculo = veiculo.copy(
                        id = UUID.randomUUID().toString(),
                        dataCadastro = Date(),
                        dataAtualizacao = Date()
                    )

                    val entity = VeiculoEntity.fromModel(novoVeiculo)
                    veiculoDao.inserirVeiculo(entity)
                    veiculosCache.add(novoVeiculo)

                    Result.success(novoVeiculo)
                }
            } catch (e: Exception) {
                Result.failure(Exception("Erro ao criar veículo: ${e.message}"))
            }
        }
    }

    override suspend fun obterVeiculoPorId(id: String): Result<Veiculo?> {
        return withContext(Dispatchers.IO) {
            try {
                // Tentar buscar da API
                val response = veiculoApi.obterVeiculoPorId(id)
                if (response.isSuccessful && response.body() != null) {
                    val veiculoApi = response.body()!!

                    // Salvar no banco local
                    val entity = VeiculoEntity.fromModel(veiculoApi)
                    veiculoDao.inserirVeiculo(entity)

                    // Atualizar cache
                    val index = veiculosCache.indexOfFirst { it.id == id }
                    if (index != -1) {
                        veiculosCache[index] = veiculoApi
                    } else {
                        veiculosCache.add(veiculoApi)
                    }

                    Result.success(veiculoApi)
                } else {
                    // Fallback 1: Buscar do banco local
                    val entity = veiculoDao.obterVeiculoPorId(id)
                    if (entity != null) {
                        Result.success(entity.toModel())
                    } else {
                        // Fallback 2: Buscar do cache
                        val veiculoCache = veiculosCache.find { it.id == id }
                        Result.success(veiculoCache)
                    }
                }
            } catch (e: Exception) {
                // Fallback final: cache
                val veiculoCache = veiculosCache.find { it.id == id }
                Result.success(veiculoCache)
            }
        }
    }

    override suspend fun atualizarVeiculo(veiculo: Veiculo): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                // Tentar atualizar na API
                val response = veiculoApi.atualizarVeiculo(veiculo.id, veiculo)
                if (response.isSuccessful) {
                    // Atualizar no banco local
                    val entity = VeiculoEntity.fromModel(veiculo.copy(dataAtualizacao = Date()))
                    veiculoDao.atualizarVeiculo(entity)

                    // Atualizar cache
                    val index = veiculosCache.indexOfFirst { it.id == veiculo.id }
                    if (index != -1) {
                        veiculosCache[index] = veiculo
                    }

                    Result.success(true)
                } else {
                    Result.failure(Exception("Falha ao atualizar veículo na API"))
                }
            } catch (e: Exception) {
                // Fallback: atualizar localmente
                val index = veiculosCache.indexOfFirst { it.id == veiculo.id }
                if (index != -1) {
                    veiculosCache[index] = veiculo
                    val entity = VeiculoEntity.fromModel(veiculo)
                    veiculoDao.atualizarVeiculo(entity)
                    Result.success(true)
                } else {
                    Result.failure(Exception("Veículo não encontrado"))
                }
            }
        }
    }

    override suspend fun deletarVeiculo(id: String): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                // Tentar deletar na API
                val response = veiculoApi.deletarVeiculo(id)
                if (response.isSuccessful) {
                    // Soft delete localmente
                    veiculoDao.desativarVeiculo(id)

                    // Remover do cache
                    veiculosCache.removeAll { it.id == id }

                    Result.success(true)
                } else {
                    Result.failure(Exception("Falha ao deletar veículo na API"))
                }
            } catch (e: Exception) {
                // Fallback: deletar localmente
                val removed = veiculosCache.removeAll { it.id == id }
                if (removed) {
                    veiculoDao.deletarVeiculoPorId(id)
                    Result.success(true)
                } else {
                    Result.failure(Exception("Veículo não encontrado"))
                }
            }
        }
    }

    // ========== CONSULTAS POR USUÁRIO ==========

    override suspend fun obterVeiculosPorUsuario(usuarioId: String): Result<List<Veiculo>> {
        return withContext(Dispatchers.IO) {
            try {
                val veiculos = veiculosCache.filter { it.usuarioId == usuarioId }
                Result.success(veiculos)
            } catch (e: Exception) {
                Result.failure(Exception("Erro ao buscar veículos: ${e.message}"))
            }
        }
    }

    override fun observarVeiculosPorUsuario(usuarioId: String): Flow<List<Veiculo>> {
        return flow {
            while (true) {
                val veiculos = veiculosCache.filter { it.usuarioId == usuarioId }
                emit(veiculos)
                kotlinx.coroutines.delay(3000) // Atualiza a cada 3 segundos
            }
        }
    }

    override suspend fun obterVeiculosAtivosPorUsuario(usuarioId: String): Result<List<Veiculo>> {
        return withContext(Dispatchers.IO) {
            try {
                val veiculos = veiculosCache.filter { it.usuarioId == usuarioId && it.ativo }
                Result.success(veiculos)
            } catch (e: Exception) {
                Result.failure(Exception("Erro ao buscar veículos ativos: ${e.message}"))
            }
        }
    }

    override fun observarVeiculosAtivosPorUsuario(usuarioId: String): Flow<List<Veiculo>> {
        return observarVeiculosPorUsuario(usuarioId)
            .map { veiculos -> veiculos.filter { it.ativo } }
    }

    // ========== CONSULTAS POR PLACA ==========

    override suspend fun obterVeiculoPorPlaca(placa: String): Result<Veiculo?> {
        return withContext(Dispatchers.IO) {
            try {
                val veiculo = veiculosCache.find { it.placa.equals(placa, ignoreCase = true) }
                Result.success(veiculo)
            } catch (e: Exception) {
                Result.failure(Exception("Erro ao buscar veículo por placa: ${e.message}"))
            }
        }
    }

    override suspend fun buscarVeiculosPorPlaca(placa: String): Result<List<Veiculo>> {
        return withContext(Dispatchers.IO) {
            try {
                val veiculos = veiculosCache.filter {
                    it.placa.contains(placa, ignoreCase = true)
                }
                Result.success(veiculos)
            } catch (e: Exception) {
                Result.failure(Exception("Erro ao buscar veículos: ${e.message}"))
            }
        }
    }

    override suspend fun verificarPlacaExistente(placa: String): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val existe = veiculosCache.any {
                    it.placa.equals(placa, ignoreCase = true)
                }
                Result.success(existe)
            } catch (e: Exception) {
                Result.failure(Exception("Erro ao verificar placa: ${e.message}"))
            }
        }
    }

    override suspend fun verificarPlacaExistenteParaUsuario(placa: String, usuarioId: String): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val existe = veiculosCache.any {
                    it.placa.equals(placa, ignoreCase = true) &&
                            it.usuarioId == usuarioId
                }
                Result.success(existe)
            } catch (e: Exception) {
                Result.failure(Exception("Erro ao verificar placa: ${e.message}"))
            }
        }
    }

    // ========== OPERAÇÕES DE STATUS ==========

    override suspend fun ativarVeiculo(id: String): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val index = veiculosCache.indexOfFirst { it.id == id }
                if (index != -1) {
                    val veiculo = veiculosCache[index]
                    veiculosCache[index] = veiculo.copy(
                        ativo = true,
                        dataAtualizacao = Date()
                    )
                    Result.success(true)
                } else {
                    Result.failure(Exception("Veículo não encontrado"))
                }
            } catch (e: Exception) {
                Result.failure(Exception("Erro ao ativar veículo: ${e.message}"))
            }
        }
    }

    override suspend fun desativarVeiculo(id: String): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val index = veiculosCache.indexOfFirst { it.id == id }
                if (index != -1) {
                    val veiculo = veiculosCache[index]
                    veiculosCache[index] = veiculo.copy(
                        ativo = false,
                        dataAtualizacao = Date()
                    )
                    Result.success(true)
                } else {
                    Result.failure(Exception("Veículo não encontrado"))
                }
            } catch (e: Exception) {
                Result.failure(Exception("Erro ao desativar veículo: ${e.message}"))
            }
        }
    }

    override suspend fun verificarVeiculoAtivo(id: String): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val veiculo = veiculosCache.find { it.id == id }
                Result.success(veiculo?.ativo ?: false)
            } catch (e: Exception) {
                Result.failure(Exception("Erro ao verificar veículo: ${e.message}"))
            }
        }
    }

    // ========== CONSULTAS ESTATÍSTICAS ==========

    override suspend fun contarVeiculosPorUsuario(usuarioId: String): Result<Int> {
        return withContext(Dispatchers.IO) {
            try {
                val quantidade = veiculosCache.count { it.usuarioId == usuarioId }
                Result.success(quantidade)
            } catch (e: Exception) {
                Result.failure(Exception("Erro ao contar veículos: ${e.message}"))
            }
        }
    }

    override suspend fun contarVeiculosAtivosPorUsuario(usuarioId: String): Result<Int> {
        return withContext(Dispatchers.IO) {
            try {
                val quantidade = veiculosCache.count {
                    it.usuarioId == usuarioId && it.ativo
                }
                Result.success(quantidade)
            } catch (e: Exception) {
                Result.failure(Exception("Erro ao contar veículos ativos: ${e.message}"))
            }
        }
    }

    override suspend fun contarTotalVeiculos(): Result<Int> {
        return withContext(Dispatchers.IO) {
            try {
                Result.success(veiculosCache.size)
            } catch (e: Exception) {
                Result.failure(Exception("Erro ao contar veículos: ${e.message}"))
            }
        }
    }

    override suspend fun contarVeiculosAtivos(): Result<Int> {
        return withContext(Dispatchers.IO) {
            try {
                val quantidade = veiculosCache.count { it.ativo }
                Result.success(quantidade)
            } catch (e: Exception) {
                Result.failure(Exception("Erro ao contar veículos ativos: ${e.message}"))
            }
        }
    }

    override suspend fun obterEstatisticasPorTipo(): Result<Map<String, Int>> {
        return withContext(Dispatchers.IO) {
            try {
                val estatisticas = veiculosCache
                    .filter { it.ativo }
                    .groupBy { it.tipo.name }
                    .mapValues { (_, veiculos) -> veiculos.size }
                Result.success(estatisticas)
            } catch (e: Exception) {
                Result.failure(Exception("Erro ao obter estatísticas: ${e.message}"))
            }
        }
    }

    override suspend fun obterTopMarcas(limit: Int): Result<Map<String, Int>> {
        return withContext(Dispatchers.IO) {
            try {
                val topMarcas = veiculosCache
                    .filter { it.ativo }
                    .groupBy { it.marca }
                    .mapValues { (_, veiculos) -> veiculos.size }
                    .toList()
                    .sortedByDescending { (_, quantidade) -> quantidade }
                    .take(limit)
                    .toMap()
                Result.success(topMarcas)
            } catch (e: Exception) {
                Result.failure(Exception("Erro ao obter top marcas: ${e.message}"))
            }
        }
    }

    // ========== CONSULTAS COM ESTATÍSTICAS AVANÇADAS ==========

    override suspend fun obterVeiculosComEstatisticasPorUsuario(usuarioId: String): Result<List<VeiculoEstatisticas>> {
        return withContext(Dispatchers.IO) {
            try {
                val veiculos = veiculosCache.filter { it.usuarioId == usuarioId }
                val estatisticas = veiculos.map { veiculo ->
                    // Em uma implementação real, você buscaria estatísticas do AcessoRepository
                    VeiculoEstatisticas(
                        veiculo = veiculo,
                        totalAcessos = (0..20).random(), // Mock de dados
                        totalGasto = (0.0..1000.0).random() // Mock de dados
                    )
                }
                Result.success(estatisticas)
            } catch (e: Exception) {
                Result.failure(Exception("Erro ao obter estatísticas: ${e.message}"))
            }
        }
    }

    override suspend fun obterVeiculoComEstatisticas(id: String): Result<VeiculoEstatisticas?> {
        return withContext(Dispatchers.IO) {
            try {
                val veiculo = veiculosCache.find { it.id == id }
                if (veiculo != null) {
                    val estatisticas = VeiculoEstatisticas(
                        veiculo = veiculo,
                        totalAcessos = (0..50).random(), // Mock
                        totalGasto = (0.0..500.0).random() // Mock
                    )
                    Result.success(estatisticas)
                } else {
                    Result.success(null)
                }
            } catch (e: Exception) {
                Result.failure(Exception("Erro ao obter estatísticas: ${e.message}"))
            }
        }
    }

    // ========== VALIDAÇÕES ==========

    override suspend fun validarPlaca(placa: String): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                // Validação básica de placa (exemplo: formato brasileiro)
                val regex = Regex("[A-Z]{3}[0-9][A-Z0-9][0-9]{2}")
                val valido = regex.matches(placa.uppercase())
                Result.success(valido)
            } catch (e: Exception) {
                Result.failure(Exception("Erro ao validar placa: ${e.message}"))
            }
        }
    }

    override suspend fun validarVeiculo(veiculo: Veiculo): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val valido = veiculo.placa.isNotBlank() &&
                        veiculo.marca.isNotBlank() &&
                        veiculo.modelo.isNotBlank() &&
                        veiculo.cor.isNotBlank() &&
                        veiculo.usuarioId.isNotBlank()
                Result.success(valido)
            } catch (e: Exception) {
                Result.failure(Exception("Erro ao validar veículo: ${e.message}"))
            }
        }
    }

    // ========== MÉTODOS NÃO IMPLEMENTADOS (TODOs) ==========

    override suspend fun obterVeiculosPorEstacionamento(estacionamentoId: String): Result<List<Veiculo>> {
        TODO("Not yet implemented")
    }

    override suspend fun obterVeiculosAtivosNoEstacionamento(estacionamentoId: String): Result<List<Veiculo>> {
        TODO("Not yet implemented")
    }

    override suspend fun obterVeiculosFrequentesNoEstacionamento(estacionamentoId: String, limit: Int): Result<List<Veiculo>> {
        TODO("Not yet implemented")
    }

    override suspend fun obterVeiculosPorTipo(tipo: String): Result<List<Veiculo>> {
        TODO("Not yet implemented")
    }

    override suspend fun obterVeiculosPorMarca(marca: String): Result<List<Veiculo>> {
        TODO("Not yet implemented")
    }

    override suspend fun buscarVeiculosPorModelo(modelo: String): Result<List<Veiculo>> {
        TODO("Not yet implemented")
    }

    override suspend fun obterVeiculosPorCor(cor: String): Result<List<Veiculo>> {
        TODO("Not yet implemented")
    }

    override suspend fun buscarVeiculos(termo: String): Result<List<Veiculo>> {
        TODO("Not yet implemented")
    }

    override suspend fun obterNovosVeiculosPorMes(meses: Int): Result<Map<String, Int>> {
        TODO("Not yet implemented")
    }

    override suspend fun criarVeiculosEmLote(veiculos: List<Veiculo>): Result<List<Veiculo>> {
        TODO("Not yet implemented")
    }

    override suspend fun atualizarVeiculosEmLote(veiculos: List<Veiculo>): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun sincronizarVeiculos(): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun obterVeiculosNaoSincronizados(): Result<List<Veiculo>> {
        TODO("Not yet implemented")
    }

    override suspend fun marcarComoComoSincronizado(id: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun criarBackupVeiculos(): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun restaurarVeiculos(backupData: String): Result<Boolean> {
        TODO("Not yet implemented")
    }
}