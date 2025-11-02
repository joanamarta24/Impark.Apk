package com.example.imparkapk.data.dao.remote.api.api

import com.example.imparkapk.data.dao.remote.api.request.AtualizarAvaliacaoRequest
import com.example.imparkapk.data.dao.remote.api.request.AvaliacaoRequest
import com.example.imparkapk.data.dao.remote.api.response.ApiResponse
import com.example.imparkapk.data.dao.remote.api.response.AvaliacaoResponse
import com.example.imparkapk.data.dao.remote.api.response.EstatisticasAvaliacaoResponse
import com.example.imparkapk.data.dao.remote.api.response.PaginatedResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface AvaliacaoApi {
    @GET("avaliacoes/estacionamento/{estacionamento_id}")
    suspend fun listarAvaliacoesPorEstacionamento(
        @Path("estacionamento_id") estacionamentoId: String,
        @Query("page") page: Int =1,
        @Query("limit") limit: Int = 20,
        @Query("ordenar_por") ordenarPor: String = "data_avaliacao"
    ): Response<ApiResponse<PaginatedResponse<AvaliacaoResponse>>>

    @GET("avaliacoes/usuario/{usuario_id}")
    suspend fun listarAvaliacoesPorUsuario(
        @Path("usuario_id") usuarioId: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<ApiResponse<PaginatedResponse<AvaliacaoResponse>>>

    @GET("avaliacoes/{id}")
    suspend fun getAvaliacao(@Path("id") id: String): Response<ApiResponse<AvaliacaoResponse>>

    @POST("avaliacoes")
    suspend fun criarAvaliacao(@Body request: AvaliacaoRequest): Response<ApiResponse<AvaliacaoResponse>>

    @PUT("avaliacoes/{id}")
    suspend fun atualizarAvaliacao(
        @Path("id") id: String,
        @Body request: AtualizarAvaliacaoRequest
    ): Response<ApiResponse<AvaliacaoResponse>>

    @DELETE("avaliacoes/{id}")
    suspend fun deletarAvaliacao(@Path("id") id: String): Response<ApiResponse<Unit>>

    // Estatísticas
    @GET("avaliacoes/estatisticas/{estacionamento_id}")
    suspend fun getEstatisticasAvaliacoes(@Path("estacionamento_id") estacionamentoId: String): Response<ApiResponse<EstatisticasAvaliacaoResponse>>

    // Verificação
    @GET("avaliacoes/verificar/{usuario_id}/{estacionamento_id}")
    suspend fun verificarPodeAvaliar(
        @Path("usuario_id") usuarioId: String,
        @Path("estacionamento_id") estacionamentoId: String
    ): Response<ApiResponse<Boolean>>
}