package com.example.imparktcc.data.repository

import com.example.imparkapk.data.dao.api.CarroApi
import com.example.imparkapk.data.dao.dao.CarroDao
import com.example.imparkapk.data.dao.entity.CarroEntity
import com.example.imparkapk.data.dao.request.CarroRequest
import com.example.imparktcc.model.Carro
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class CarroRepositoryImpl @Inject constructor(
    private val carroDao: CarroDao,
    private val carroApi: CarroApi
) : CarroRepository {

    override suspend fun cadastrarCarro(carro: Carro): Result<Boolean> {
        return try {
            // Tenta cadastrar na API
            val request = CarroRequest(
                usuarioId = carro.usuarioId,
                modelo = carro.modelo,
                placa = carro.placa,
                cor = carro.cor
            )

            val response = carroApi.criarCarro(request)

            if (response.isSuccessful && response.body() != null) {
                val carroResponse = response.body()!!

                // Salva no banco local
                val entity = CarroEntity(
                    id = carroResponse.id,
                    usuarioId = carroResponse.usuarioId,
                    modelo = carroResponse.modelo,
                    placa = carroResponse.placa,
                    cor = carroResponse.cor,
                    dataCriacao = carroResponse.dataCriacao,
                    dataAtualizacao = carroResponse.dataAtualizacao,
                    ativo = carroResponse.ativo
                )

                carroDao.insertCarro(entity)
                Result.success(true)
            } else {
                // Fallback: salva apenas localmente
                val entity = CarroEntity(
                    id = UUID.randomUUID().toString(),
                    usuarioId = carro.usuarioId,
                    modelo = carro.modelo,
                    placa = carro.placa,
                    cor = carro.cor,
                    dataCriacao = Date(),
                    dataAtualizacao = Date(),
                    ativo = true
                )

                carroDao.insertCarro(entity)
                Result.success(true)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCarroPorId(id: String): Result<Carro?> {
        return try {
            val entity = carroDao.getCarroById(id)
            val carro = entity?.toCarro()
            Result.success(carro)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCarroPorPlaca(placa: String): Result<Carro?> {
        return try {
            val entity = carroDao.getCarroPorPlaca(placa)
            val carro = entity?.toCarro()
            Result.success(carro)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun atualizarCarro(carro: Carro): Result<Boolean> {
        return try {
            val entity = CarroEntity(
                id = carro.id,
                usuarioId = carro.usuarioId,
                modelo = carro.modelo,
                placa = carro.placa,
                cor = carro.cor,
                dataCriacao = Date(), // Em produção, buscar do banco
                dataAtualizacao = Date(),
                ativo = true
            )

            carroDao.updateCarro(entity)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deletarCarro(id: String): Result<Boolean> {
        return try {
            carroDao.deleteCarro(id)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getCarrosPorUsuario(usuarioId: String): Flow<List<Carro>> {
        return carroDao.getCarrosPorUsuario(usuarioId).map { entities ->
            entities.map { it.toCarro() }
        }
    }

    override suspend fun countCarrosPorUsuario(usuarioId: String): Result<Int> {
        return try {
            val count = carroDao.countCarrosPorUsuario(usuarioId)
            Result.success(count)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Extension function para converter Entity para Model
    private fun CarroEntity.toCarro(): Carro {
        return Carro(
            id = this.id,
            usuarioId = this.usuarioId,
            modelo = this.modelo,
            placa = this.placa,
            cor = this.cor
        )
    }
}