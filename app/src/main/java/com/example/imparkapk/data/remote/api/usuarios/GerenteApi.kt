package com.example.imparkapk.data.remote.api.usuarios

import com.example.imparkapk.data.remote.dto.usuarios.GerenteDto
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface GerenteApi {
    @GET("gerente")
    suspend fun list() : List<GerenteDto>

    @GET("gerente/{id}")
    suspend fun getById(@Path("id") id: Long) : GerenteDto

    @POST("gerente")
    suspend fun create(@Body dadosJson: RequestBody) : GerenteDto

    @PUT("gerente/{id}")
    suspend fun update(
        @Path(value = "id") id: Long,
        @Body dadosJson: RequestBody
    ) : GerenteDto

    @DELETE("gerente/{id}")
    suspend fun delete(@Path(value = "id") id: Long)
}