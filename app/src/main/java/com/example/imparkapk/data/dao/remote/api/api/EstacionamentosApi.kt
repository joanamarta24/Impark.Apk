package com.example.imparkapk.data.dao.remote.api.api

import com.example.imparkapk.data.dao.remote.api.dto.PaginationDto
import com.example.imparkapk.data.dao.remote.api.request.AtualizarEstacionamentoRequest
import com.example.imparkapk.data.dao.remote.api.request.BuscarEstacionamentosRequest
import com.example.imparkapk.data.dao.remote.api.request.EstacionamentoRequest
import com.example.imparkapk.data.dao.remote.api.request.VeiculoResponse
import com.example.imparkapk.data.dao.remote.api.response.ApiResponse
import com.example.imparkapk.data.dao.remote.api.response.EstacionamentoDetalhesResponse
import com.example.imparkapk.data.dao.remote.api.response.EstacionamentoResponse
import com.example.imparkapk.data.dao.remote.api.response.PaginatedResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface EstacionamentosApi {

    @GET("estacionamentos")
    suspend fun listarEstacionamentos(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20,
        @Query("sort_by") sortBy: String? = null,
        @Query("sort_order") sortOrder: String = "asc"
    ): Response<ApiResponse<PaginatedResponse<EstacionamentoResponse>>>

    @POST("estacionamentos/buscar")
    suspend fun buscarEstacionamentos(
        @Body request: BuscarEstacionamentosRequest
    ): Response<ApiResponse<PaginationDto<EstacionamentoResponse>>>

    @GET("estacionamentos/{id}/veiculos")
    suspend fun getVeiculosEstacionamento(
        @Path("id") id: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 50
    ): Response<ApiResponse<PaginationDto<VeiculoResponse>>>

    @POST("estacionamentos/buscar")
    suspend fun buscarEstacionamentos(
        @Body request: BuscarEstacionamentosRequest
    ): Response<ApiResponse<PaginatedResponse<EstacionamentoResponse>>>

    @GET("estacionamentos/{id}")
    suspend fun getEstacionamento(@Path("id") id: String): Response<ApiResponse<EstacionamentoResponse>>

    @GET("estacionamentos/{id}/detalhes")
    suspend fun getEstacionamentoDetalhes(@Path("id") id: String): Response<ApiResponse<EstacionamentoDetalhesResponse>>

    @POST("estacionamentos")
    suspend fun criarEstacionamento(@Body request: EstacionamentoRequest): Response<ApiResponse<EstacionamentoResponse>>

    @PUT("estacionamentos/{id}")
    suspend fun atualizarEstacionamento(
        @Path("id") id: String,
        @Body request: AtualizarEstacionamentoRequest
    ): Response<ApiResponse<EstacionamentoResponse>>

    @DELETE("estacionamentos/{id}")
    suspend fun deletarEstacionamento(@Path("id") id: String): Response<ApiResponse<Unit>>

    @PUT("estacionamentos/{id}/vagas")
    suspend fun atualizarVagasDisponiveis(
        @Path("id") id: String,
        @Query("vagas") vagas: Int
    ): Response<ApiResponse<EstacionamentoResponse>>

    // Estatísticas
    @GET("estacionamentos/{id}/estatisticas")
    suspend fun getEstatisticasEstacionamento(@Path("id") id: String): Response<ApiResponse<Map<String, Any>>>

    @GET("estacionamentos/proximos")
    suspend fun getEstacionamentosProximos(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("raio_km") raioKm: Double = 5.0
    ): Response<ApiResponse<List<EstacionamentoResponse>>>

    // Serviços
    @GET("estacionamentos/servicos")
    suspend fun listarServicos(): Response<ApiResponse<List<String>>>
}