package com.example.imparkapk.data.remote.api.api

import com.example.imparkapk.data.remote.dto.CarroDto
import okhttp3.RequestBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface CarroApi {
    @GET("carro")
    suspend fun list() : List<CarroDto>

    @GET("carro/{id}")
    suspend fun getById(@Path("id") id: Long) : CarroDto

    @POST("carro")
    suspend fun create(@Part(value = "dados") dadosJson: RequestBody) : CarroDto

    @PUT("carro/{id}")
    suspend fun update(
        @Path(value = "id") id: Long,
        @Part(value = "dados") dadosJson: RequestBody
    ) : CarroDto

    @DELETE("carro/{id}")
    suspend fun delete(@Path(value = "id") id: Long)
}