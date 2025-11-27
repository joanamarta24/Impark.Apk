package com.example.imparkapk.data.remote.api

import com.example.imparkapk.data.remote.dto.EstacionamentoDto
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface EstacionamentoApi {
    @GET("estacionamento")
    suspend fun list() : List<EstacionamentoDto>

    @GET("estacionamento/{id}")
    suspend fun getById(@Path("id") id: Long) : EstacionamentoDto

    @POST("estacionamento")
    suspend fun create(@Body dadosJson: RequestBody) : EstacionamentoDto

    @PUT("estacionamento/{id}")
    suspend fun update(
        @Path(value = "id") id: Long,
        @Body dadosJson: RequestBody
    ) : EstacionamentoDto

    @DELETE("estacionamento/{id}")
    suspend fun delete(@Path(value = "id") id: Long)
}