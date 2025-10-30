package com.example.imparkapk.data.dao.repository

import com.example.imparkapk.data.dao.api.EstacionamentoApi
import com.example.imparkapk.data.dao.dao.EstacionamentoDao
import com.example.imparkapk.data.dao.model.Estacionamento
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import org.checkerframework.checker.regex.qual.Regex
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EstacionamentoRepositoryImpl @Inject constructor(
    private val estacionamentoDao: EstacionamentoDao,
    private val estacionamentoApi: EstacionamentoApi
): EstacionamentoRepository{
    init {
        estacionamentosCache.addAll(listOf(
            Estacionamento(
                id = "1",
                nome = "Estacionamento Central",
                endereco = "Rua Principal,123-Centro",
                latitude = -23.5505,
                longitude = -46.6333,
                totalVagas = 50,
                vagasDisponiveis = 15,
                valorHora = 8.50,
                telefone = "(11) 9999-8888",
                horarioAbertura = "06:00",
                horarioFechamento = "22:00"
            ),
            Estacionamento(
                id = "2",
                nome = "Parking Shopping",
                endereco = "Av.Comercial, 456 - Jardins",
                latitude = -23.5632,
                longitude = -46.6544,
                totalVagas = 200,
                vagasDisponiveis = 45,
                valorHora = 12.00,
                telefone = "(11) 9777-6666",
                horarioAbertura = "08:00",
                horarioFechamento = "23:00"
            ),
            Estacionamento(
                id = "3",
                nome = "Estacionamento Espress",
                endereco = "Rua Rápida, 789-Centro",
                latitude = -23.5489,
                longitude = -46.6388,
                totalVagas = 30,
                vagasDisponiveis = 8,
                valorHora = 6.00,
                telefone = "(11) 9555-4444",
                horarioAbertura = "07:00",
                horarioFechamento = "21:00"
            )

        ))
    }
    private val estacionamentosCache = mutableListOf<Estacionamento>()
    override suspend fun cadastrarEstacionamento(estacionamento: Estacionamento): Boolean {
        return try {
            delay(2500)
            val novoEstacionamento = estacionamento.copy(id = UUID.randomUUID().toString())
            estacionamentosCache.add(novoEstacionamento)
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getEstacionamentoPorId(id: String): Estacionamento? {
        delay(1000)
        return estacionamentosCache.find { it.id == id }
    }

    override suspend fun listarEstacionamentos(): List<Estacionamento> {
        delay(1200)
        return estacionamentosCache
    }

    override suspend fun listarEstacionamentosComVagas(): List<Estacionamento>{
        delay(800)
        return estacionamentosCache.filter { it.vagasDisponiveis > 0 }
    }

    override suspend fun atualizarEstacionamento(estacionamento: Estacionamento): Boolean{
        return try {
            delay(1800)
            val index = estacionamentosCache.indexOfFirst { it.id == estacionamento.id }
            if (index != -1){
                estacionamentosCache[index] = estacionamento
                true
            }else{
                false
            }
        }catch (e: Exception){
            false
        }
    }

    override suspend fun atualizarVagasDisponiveis(id: String,vagas: Int): Boolean{
        return try {
            delay(500)
            val estacionamento = estacionamentosCache.find { it.id == id}
            if (estacionamento != null){
                val index = estacionamentosCache.indexOf(estacionamento)
                estacionamentosCache[index] =estacionamento.copy(vagasDisponiveis = vagas)
                true
            }else{
                false
            }
        }catch (e: Exception){
            false
        }
    }
    override suspend fun buscarEstacionamentosPorNome(nome: String): List<Estacionamento>{
        delay(800)
        return estacionamentosCache.filter {
            it.nome.contains(nome, ignoreCase = true)
        }
    }
    override suspend fun buscarEstacionamentoProximos(
        latitude: Double,
        longitude: Double,
        raioKm: Double,
    ): List<Estacionamento>{
        delay(1500)
        return estacionamentosCache.take(2)
    }
    override suspend fun buscarEstacionamentosPorPreco(maxPreco: Double): List<Estacionamento>{
        delay(800)
        return estacionamentosCache.filter { it.valorHora <= maxPreco }
    }
    override fun validarCoordenadas(latitude: Double,longitude: Double): Boolean{
        return latitude in -90.0..90.0 && longitude in -180.0..180.0
    }
    override fun validarHorario(horario: String): Boolean{
        val horarioRegex = Regex("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$")
        return horarioRegex.matches(horario)
    }
    override fun validarTelefone(telefone: String): Boolean{
        val telefoneRegex = Regex("^\\([1-9]{2}\\) [0-9]{4,5}-[0-9]{4}$")
        return telefoneRegex.matches(telefone)
    }
    override suspend fun getMediaAvaliacoes(estacionamentoId: String): Double{
        delay(600)
        return (3.5 + (0..15).random() *0.1).coerceIn(1.0,5.0)
    }
    override suspend fun countEstacionamento(): Int{
        delay(300)
        return estacionamentosCache.size
    }
    override suspend fun getTaxaOcupacao(estacionamentoId: String): Double{
        delay(500)
        val estacionamento = estacionamentosCache.find { it.id == estacionamentoId }
        return if (estacionamento != null){
            val vagasOcupadas = estacionamento.totalVagas - estacionamento.vagasDisponiveis
            (vagasOcupadas.toDouble() / estacionamento.totalVagas) *100
        }else{
            0.0
        }
    }
}