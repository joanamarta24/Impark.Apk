package com.example.imparkapk.data.dao.api

import androidx.room.Query
import com.example.imparkapk.data.dao.request.UsuarioRequest
import com.example.imparkapk.data.dao.response.UsuarioResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UsuarioApi {

    @POST("usuarios")
    suspend fun criarUsuario(@Body request: UsuarioRequest): Response<UsuarioResponse>

    @GET("usuarios/{id}")
    suspend fun getUsuario(@Path("id") id: String): Response<UsuarioResponse>

    @GET("usuarios")
    suspend fun getUsuarioPorEmail(@Query("email") email: String): Response<UsuarioResponse>

    @PUT("usuarios/{id}")
    suspend fun atualizarUsuario(
        @Path("id") id: String,
        @Body request: UsuarioRequest
    ): Response<UsuarioResponse>

    @DELETE("usuarios/{id}")
    suspend fun deletarUsuario(@Path("id") id: String): Response<Unit>
}