package com.example.imparkapk.data.dao.remote.api.repository.estacionamento

import android.util.Log
import com.example.imparkapk.data.dao.remote.api.api.EstacionamentoApi
import com.example.imparkapk.data.dao.local.dao.dao.EstacionamentoDao
import com.example.imparkapk.data.dao.local.dao.entity.EstacionamentoEntity
import com.example.imparkapk.data.dao.model.Estacionamento
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EstacionamentoRepositoryImpl @Inject constructor(
    private val estacionamentoDao: EstacionamentoDao,
    private val estacionamentoApi: EstacionamentoApi
) : EstacionamentoRepository {

    private val estacionamentosCache = mutableListOf<Estacionamento>().apply {
        addAll(listOf(
            Estacionamento(
                id = "1",
                nome = "Estacionamento Central",
                endereco = "Rua Principal, 123 - Centro",
                latitude = -23.5505,
                longitude = -46.6333,
                totalVagas = 50,
                vagasDisponiveis = 15,
                valorHora = 8.50,
                telefone = "(11) 9999-8888",
                horarioAbertura = "06:00",
                horarioFechamento = "22:00",
                ativo = true
            ),
            Estacionamento(
                id = "2",
                nome = "Parking Shopping",
                endereco = "Av. Comercial, 456 - Jardins",
                latitude = -23.5632,
                longitude = -46.6544,
                totalVagas = 200,
                vagasDisponiveis = 45,
                valorHora = 12.00,
                telefone = "(11) 9777-6666",
                horarioAbertura = "08:00",
                horarioFechamento = "23:00",
                ativo = true
            ),
            Estacionamento(
                id = "3",
                nome = "Estacionamento Express",
                endereco = "Rua Rápida, 789 - Centro",
                latitude = -23.5489,
                longitude = -46.6388,
                totalVagas = 30,
                vagasDisponiveis = 8,
                valorHora = 6.00,
                telefone = "(11) 9555-4444",
                horarioAbertura = "07:00",
                horarioFechamento = "21:00",
                ativo = true
            )
        ))
    }

    override suspend fun cadastrarEstacionamento(estacionamento: Estacionamento): Result<Boolean> {
        return try {
            delay(1500)

            // Validações
            if (!validarCoordenadas(estacionamento.latitude, estacionamento.longitude)) {
                return Result.failure(IllegalArgumentException("Coordenadas inválidas"))
            }
            if (!validarHorario(estacionamento.horarioAbertura) || !validarHorario(estacionamento.horarioFechamento)) {
                return Result.failure(IllegalArgumentException("Horário inválido"))
            }
            if (!validarTelefone(estacionamento.telefone)) {
                return Result.failure(IllegalArgumentException("Telefone inválido"))
            }

            val novoEstacionamento = estacionamento.copy(id = UUID.randomUUID().toString())
            estacionamentosCache.add(novoEstacionamento)

            // Salva no banco local também
            estacionamentoDao.insertEstacionamento(novoEstacionamento.toEntity())

            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getEstacionamentoPorId(id: String): Result<Estacionamento?> {
        return try {
            delay(800)
            val estacionamento = estacionamentosCache.find { it.id == id }
            Result.success(estacionamento)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun listarEstacionamentos(): Result<List<Estacionamento>> {
        return try {
            delay(1000)
            Result.success(estacionamentosCache)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun listarEstacionamentosComVagas(): Result<List<Estacionamento>> {
        return try {
            delay(600)
            val estacionamentos = estacionamentosCache.filter { it.vagasDisponiveis > 0 && it.ativo }
            Result.success(estacionamentos)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun atualizarEstacionamento(estacionamento: Estacionamento): Result<Boolean> {
        return try {
            delay(1200)
            val index = estacionamentosCache.indexOfFirst { it.id == estacionamento.id }
            if (index != -1) {
                estacionamentosCache[index] = estacionamento

                // Atualiza no banco local
                estacionamentoDao.updateEstacionamento(estacionamento.toEntity())
                Result.success(true)
            } else {
                Result.success(false)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun atualizarVagasDisponiveis(id: String, vagas: Int): Result<Boolean> {
        return try {
            delay(400)
            val estacionamento = estacionamentosCache.find { it.id == id }
            if (estacionamento != null) {
                val index = estacionamentosCache.indexOf(estacionamento)
                val updated = estacionamento.copy(vagasDisponiveis = vagas)
                estacionamentosCache[index] = updated
                // Atualiza no banco local
                estacionamentoDao.updateVagasDisponiveis(id, vagas)
                Result.success(true)
            } else {
                Result.success(false)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun buscarEstacionamentoPorId(id: String): Estacionamento? {
        return withContext(Dispatchers.IO) {
            try {
                // Buscar no banco local primeiro
                val estacionamentoLocal = estacionamentoDao.buscarPorId(id)

                if (estacionamentoLocal != null) {
                    // Se encontrou localmente, converter para domain model
                    return@withContext estacionamentoLocal.toEstacionamento()
                }

                // Se não encontrou localmente, buscar na API
                val response = estacionamentoApiService.buscarPorId(id)

                if (response.isSuccessful && response.body() != null) {
                    val estacionamentoApi = response.body()!!
                    // Salvar no banco local para cache
                    estacionamentoDao.inserir(estacionamentoApi.toEstacionamentoEntity())
                    return@withContext estacionamentoApi
                } else {
                    // Log de erro
                    Log.e("EstacionamentoRepo", "Erro ao buscar estacionamento por ID: ${response.message()}")
                    return@withContext null
                }
            } catch (e: Exception) {
                Log.e("EstacionamentoRepo", "Erro ao buscar estacionamento por ID: ${e.message}", e)
                return@withContext null
            }
        }
    }


    override suspend fun buscarEstacionamentoPorNome(nome: String): List<Estacionamento> {
        TODO("Not yet implemented")
    }

    override suspend fun buscarEstacionamentoProximos(
        latitude: Double,
        longitude: Double,
        raioKm: Double
    ): List<Estacionamento> {
        TODO("Not yet implemented")
    }

    override suspend fun buscarEstacionamentoPorPreco(maxPreco: Double): List<Estacionamento> {
        TODO("Not yet implemented")
    }

    override suspend fun buscarEstacionamentoComFiltros(
        nome: String?,
        maxPreco: Double?,
        latitude: Double?,
        longitude: Double?,
        raioKm: Double?,
        comVagas: Boolean
    ): List<Estacionamento> {
        TODO("Not yet implemented")
    }

    suspend fun buscarEstacionamentosPorNome(nome: String): Result<List<Estacionamento>> {
        return try {
            delay(600)
            val resultados = estacionamentosCache.filter {
                it.nome.contains(nome, ignoreCase = true) && it.ativo
            }
            Result.success(resultados)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun buscarEstacionamentosProximos(
        latitude: Double,
        longitude: Double,
        raioKm: Double
    ): Result<List<Estacionamento>> {
        return try {
            delay(1200)

            // Calcula todas as distâncias uma única vez
            val estacionamentosComDistancia = estacionamentosCache
                .filter { it.ativo }
                .map { estacionamento ->
                    val distancia = calcularDistancia(
                        latitude, longitude,
                        estacionamento.latitude, estacionamento.longitude
                    )
                    Triple(estacionamento, distancia, distancia <= raioKm)
                }

            val estacionamentosProximos = estacionamentosComDistancia
                .filter { (_, _, estaDentroDoRaio) -> estaDentroDoRaio }
                .sortedBy { (_, distancia, _) -> distancia }
                .map { (estacionamento, _, _) -> estacionamento }

            Result.success(estacionamentosProximos)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun buscarEstacionamentosPorPreco(maxPreco: Double): Result<List<Estacionamento>> {
        return try {
            delay(600)
            val resultados = estacionamentosCache.filter {
                it.valorHora <= maxPreco && it.ativo
            }.sortedBy { it.valorHora }
            Result.success(resultados)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    override suspend fun getMediaAvaliacoes(estacionamentoId: String): Result<Double> {
        return try {
            delay(500)

            // Simulação de avaliações

            val media = (3.5 + (0..15).random() * 0.1).coerceIn(1.0, 5.0)
            Result.success(media)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun countEstacionamentos(): Result<Int> {
        return try {
            delay(200)
            val count = estacionamentosCache.count { it.ativo }
            Result.success(count)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTaxaOcupacao(estacionamentoId: String): Result<Double> {
        return try {
            delay(400)
            val estacionamento = estacionamentosCache.find { it.id == estacionamentoId }
            val taxa = if (estacionamento != null && estacionamento.totalVagas > 0) {
                val vagasOcupadas = estacionamento.totalVagas - estacionamento.vagasDisponiveis
                (vagasOcupadas.toDouble() / estacionamento.totalVagas) * 100
            } else {
                0.0
            }
            Result.success(taxa)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Métodos de validação
    override fun validarCoordenadas(latitude: Double, longitude: Double): Boolean {
        return latitude in -90.0..90.0 && longitude in -180.0..180.0
    }

    override fun validarHorario(horario: String): Boolean {
        val horarioRegex = Regex("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$")
        return horarioRegex.matches(horario)
    }

    override fun validarTelefone(telefone: String): Boolean {
        val telefoneRegex = Regex("^\\([1-9]{2}\\) [0-9]{4,5}-[0-9]{4}$")
        return telefoneRegex.matches(telefone)
    }

    override fun validarPreco(preco: Double): Boolean {
        return preco >= 0.0
    }

    // Métodos adicionais úteis
    suspend fun sincronizarComServidor(): Result<Boolean> {
        return try {
            delay(2000)
            // Implementar sincronização com a API
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getEstacionamentosFlow(): Flow<List<Estacionamento>> {
        return estacionamentoDao.getEstacionamentosAtivos()
            .map { entities -> entities.map { it.toModel() } }
    }

    private fun calcularDistancia(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val earthRadius = 6371.0 // km

        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)

        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2)

        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

        return earthRadius * c
    }
}

// Extensions para conversão entre Entity e Model
private fun Estacionamento.toEntity(): EstacionamentoEntity {
    return EstacionamentoEntity(
        id = this.id,
        nome = this.nome,
        endereco = this.endereco,
        vagasTotal = this.totalVagas,
        vagasDisponiveis = this.vagasDisponiveis,
        precoHora = this.valorHora,
        horarioFuncionamento = "$horarioAbertura - $horarioFechamento",
        telefone = this.telefone,
        latitude = this.latitude,
        longitude = this.longitude,
        ativo = this.ativo
    )
}

private fun EstacionamentoEntity.toModel(): Estacionamento {
    val horarios = this.horarioFuncionamento.split(" - ")
    return Estacionamento(
        id = this.id,
        nome = this.nome,
        endereco = this.endereco,
        latitude = this.latitude,
        longitude = this.longitude,
        totalVagas = this.vagasTotal,
        vagasDisponiveis = this.vagasDisponiveis,
        valorHora = this.precoHora,
        telefone = this.telefone,
        horarioAbertura = horarios.getOrElse(0) { "08:00" },
        horarioFechamento = horarios.getOrElse(1) { "18:00" },
        ativo = this.ativo
    )
}