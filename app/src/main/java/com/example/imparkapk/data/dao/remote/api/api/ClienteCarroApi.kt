package com.example.imparkapk.data.dao.remote.api.api

import com.example.imparkapk.data.dao.remote.api.response.CarroResponse
import com.example.imparkapk.data.dao.remote.api.request.CarroRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ClienteCarroApi {

    @POST("carros")
    suspend fun criarCarro(@Body request: CarroRequest): Response<CarroResponse>

    @GET("carros/{id}")
    suspend fun getCarro(@Path("id") id: String): Response<CarroResponse>

    @GET("carros")
    suspend fun getCarrosPorUsuario(@Query("usuario_id") usuarioId: String): Response<List<CarroResponse>>

    @GET("carros")
    suspend fun getCarroPorPlaca(@Query("placa") placa: String): Response<CarroResponse>

    @PUT("carros/{id}")
    suspend fun atualizarCarro(
        @Path("id") id: String,
        @Body request: CarroRequest
    ): Response<CarroResponse>

    @DELETE("carros/{id}")
    suspend fun deletarCarro(@Path("id") id: String): Response<Unit>
}