package com.example.imparkapk.data.dao.remote.api.api.usuarios

import com.example.imparkapk.data.dao.remote.api.response.UsuarioResponse
import com.example.imparkapk.data.dao.remote.api.request.UsuarioRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ClienteApi {

    @POST("cliente")
    suspend fun criarUsuario(
        @Body request: UsuarioRequest
    ): Response<UsuarioResponse>

    @GET("cliente/{id}")
    suspend fun getUsuario(
        @Path("id") id: String
    ): Response<UsuarioResponse>

    // Altere conforme o endpoint real do backend
    @GET("cliente")
    suspend fun getUsuarioPorEmail(
        @Query("email") email: String
    ): Response<UsuarioResponse>

    @PUT("clientes/{id}")
    suspend fun atualizarUsuario(
        @Path("id") id: String,
        @Body request: UsuarioRequest
    ): Response<UsuarioResponse>

    @DELETE("cliente/{id}")
    suspend fun deletarUsuario(
        @Path("id") id: String
    ): Response<Unit>
}
