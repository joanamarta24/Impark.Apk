package com.example.imparkapk.data.dao.remote.api.api

import com.example.imparkapk.data.dao.model.Veiculo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface VeiculoApi {
    @GET("veiculos/{id}")
    suspend fun obterVeiculosPorId(@Path("id") id: String): Response<Veiculo>

    @POST("veiculos")
    suspend fun criarVeiculo(@Body veiculo: Veiculo): Response<Veiculo>

    @PUT("veiculos/{id}")
    suspend fun atualizarVeiculo(@Path("id") id: String, @Body veiculo: Veiculo): Response<Veiculo>

    @DELETE("veiculos/{id}")
    suspend fun deletarVeiculo(@Path("id") id: String): Response<Void>
}