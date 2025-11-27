package com.example.imparkapk.data.remote.api.usuarios

import com.example.imparkapk.data.remote.dto.usuarios.DonoDto
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface DonoApi {
    @GET("dono")
    suspend fun list() : List<DonoDto>

    @GET("dono/{id}")
    suspend fun getById(@Path("id") id: Long) : DonoDto

    @POST("dono")
    suspend fun create(@Body dadosJson: RequestBody) : DonoDto

    @PUT("dono/{id}")
    suspend fun update(
        @Path(value = "id") id: Long,
        @Body dadosJson: RequestBody
    ) : DonoDto

    @DELETE("dono/{id}")
    suspend fun delete(@Path(value = "id") id: Long)
}