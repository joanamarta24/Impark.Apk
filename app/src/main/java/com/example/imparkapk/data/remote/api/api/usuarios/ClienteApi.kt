package com.example.imparkapk.data.remote.api.api.usuarios

import com.example.imparkapk.data.remote.dto.usuarios.ClienteDto
import okhttp3.RequestBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ClienteApi {
    @GET("cliente")
    suspend fun list() : List<ClienteDto>

    @GET("cliente/{id}")
    suspend fun getById(@Path("id") id: Long) : ClienteDto

    @POST("cliente")
    suspend fun create(@Part(value = "dados") dadosJson: RequestBody) : ClienteDto

    @PUT("cliente/{id}")
    suspend fun update(
        @Path(value = "id") id: Long,
        @Part(value = "dados") dadosJson: RequestBody
    ) : ClienteDto

    @DELETE("cliente/{id}")
    suspend fun delete(@Path(value = "id") id: Long)
}