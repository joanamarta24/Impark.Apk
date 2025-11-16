package com.example.imparkapk.data.dao.remote.api.api.usuarios

import com.example.imparkapk.data.dao.remote.api.request.GerenteRequest
import com.example.imparkapk.data.dao.remote.api.response.ApiResponse
import com.example.imparkapk.data.dao.remote.api.response.AtualizarReservaRequest
import com.example.imparkapk.data.dao.remote.api.response.GerenteResponse
import com.example.imparkapk.data.dao.remote.api.response.PaginatedResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface GerenteApi {
    @GET("gerente/estacionamento/{estacionamento_id}")
    suspend fun listarGerentesPorEstacionamento(
        @Path("estacionamento_id") estacionamento_id: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<ApiResponse<PaginatedResponse<GerenteResponse>>>
    @GET("gerentes/{id}")
    suspend fun getGerente(@Path("id") id: String): Response<ApiResponse<GerenteResponse>>

    @GET("gerentes")
    suspend fun getGerentePorUsuario(
        @Query("usuario_id") usuario_id: String,
        @Query("estacionamento_id") estacionamento_id: String
    ): Response<ApiResponse<GerenteResponse>>

    @POST("gerentes")
    suspend fun criarGerente(@Body request: GerenteRequest): Response<ApiResponse<GerenteResponse>>

    @PUT("gerentes/{id")
    suspend fun atualizarGerente(
        @Path("id") id: String,
        @Body request: AtualizarReservaRequest
    ): Response<ApiResponse<GerenteResponse>>

    @DELETE("gerentes/{id}")
    suspend fun deletarGerente(@Path("id") id: String): Response<ApiResponse<Unit>>

    @POST("gerentes/{id}/ativar")
    suspend fun ativarGerente(@Path("id") id: String): Response<ApiResponse<GerenteResponse>>

    @POST("gerentes/{id}/desativar")
    suspend fun desativarGerente(@Path("id") id: String): Response<ApiResponse<GerenteResponse>>

   //PERMISSÕES
   @GET("gerentes/{id}/permissoes")
   suspend fun getPermissoesGerente(@Path("id") id: String): Response<ApiResponse<List<String>>>

    @PUT("gerentes/{id}/permissoes")
    suspend fun atualizarPermissoesGerente(
        @Path("id") id: String,
        @Body permissoes: List<String>
    ): Response<ApiResponse<GerenteResponse>>

}