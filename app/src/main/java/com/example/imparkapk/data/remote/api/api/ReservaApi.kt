package com.example.imparkapk.data.remote.api.api

import com.example.imparkapk.data.remote.dto.ReservaDto
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ReservaApi {
    @GET("reserva")
    suspend fun list() : List<ReservaDto>

    @GET("reserva/{id}")
    suspend fun getById(@Path("id") id: Long) : ReservaDto

    @POST("reserva")
    suspend fun create(@Body dadosJson: RequestBody) : ReservaDto

    @PUT("reserva/{id}")
    suspend fun update(
        @Path(value = "id") id: Long,
        @Body dadosJson: RequestBody
    ) : ReservaDto

    @DELETE("reserva/{id}")
    suspend fun delete(@Path(value = "id") id: Long)
}