package com.example.imparkapk.data.dao.remote.api.repository.carro

import com.example.imparkapk.model.Carro
import kotlinx.coroutines.flow.Flow

interface CarroRepository{
    suspend fun cadastrarCarro(carro: Carro)
    suspend fun getCarroPorId(id: String)
    suspend fun getCarroPorPlaca(placa: String)
    suspend fun atualizarCarro(carro: Carro)
    suspend fun deletarCarro(id: String)
    suspend fun getCarrosPorUsuario(usuarioId: String): Flow<List<Carro>>
    suspend fun countCarrosPorUsuario(usuarioId: String)
}