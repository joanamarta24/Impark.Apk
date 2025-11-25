package com.example.imparkapk.data.remote.api.api

import com.example.imparkapk.data.remote.dto.AcessoDto
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface AcessoApi {
    @GET("acesso")
    suspend fun list() : List<AcessoDto>

    @GET("acesso/{id}")
    suspend fun getById(@Path("id") id: Long) : AcessoDto

    @POST("acesso")
    suspend fun create(@Body dadosJson: RequestBody) : AcessoDto

    @PUT("acesso/{id}")
    suspend fun update(
        @Path(value = "id") id: Long,
        @Body dadosJson: RequestBody
    ) : AcessoDto

    @DELETE("acesso/{id}")
    suspend fun delete(@Path(value = "id") id: Long)
}