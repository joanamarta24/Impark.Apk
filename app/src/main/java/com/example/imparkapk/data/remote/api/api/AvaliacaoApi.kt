package com.example.imparkapk.data.remote.api.api

import com.example.imparkapk.data.remote.dto.AvaliacaoDto
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface AvaliacaoApi {
    @GET("avaliacao")
    suspend fun list() : List<AvaliacaoDto>

    @GET("avaliacao/{id}")
    suspend fun getById(@Path("id") id: Long) : AvaliacaoDto

    @POST("avaliacao")
    suspend fun create(@Body dadosJson: RequestBody) : AvaliacaoDto

    @PUT("avaliacao/{id}")
    suspend fun update(
        @Path(value = "id") id: Long,
        @Body dadosJson: RequestBody
    ) : AvaliacaoDto

    @DELETE("avaliacao/{id}")
    suspend fun delete(@Path(value = "id") id: Long)
}