package com.example.imparkapk.data.dao.remote.api.api

import com.example.imparkapk.data.dao.remote.api.response.ApiResponse
import com.example.imparkapk.data.dao.remote.api.response.AtualizarReservaRequest
import com.example.imparkapk.data.dao.remote.api.response.ConfirmacaoReservaResponse
import com.example.imparkapk.data.dao.remote.api.response.DisponibilidadeResponse
import com.example.imparkapk.data.dao.remote.api.response.PaginatedResponse
import com.example.imparkapk.data.dao.remote.api.response.ReservaResponse
import com.example.imparkapk.data.dao.remote.api.response.VerificarDisponibilidadeResponse
import com.example.imparkapk.data.dao.remote.api.request.ReservaRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ReservaApi {

    // Listar reservas do usuário
    @GET("reservas")
    suspend fun listarReservasPorUsuario(
        @Query("usuario_id") usuarioId: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20,
        @Query("status") status: String? = null
    ): Response<ApiResponse<PaginatedResponse<ReservaResponse>>>

    // Listar reservas por estacionamento
    @GET("reservas/estacionamento/{estacionamentoId}")
    suspend fun listarReservasPorEstacionamento(
        @Path("estacionamentoId") estacionamentoId: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<ApiResponse<PaginatedResponse<ReservaResponse>>>

    // Obter reserva por ID
    @GET("reservas")
    suspend fun getReservas(): Response<ApiResponse<List<ReservaResponse>>>

    @GET("reservas/{id}")
    suspend fun getReserva(@Path("id") id: String): Response<ApiResponse<ReservaResponse>>

    @GET("reservas/usuario/{usuarioId}")
    suspend fun getReservasPorUsuario(@Path("usuarioId") usuarioId: String): Response<ApiResponse<List<ReservaResponse>>>

    @POST("reservas")
    suspend fun criarReserva(@Body request: ReservaRequest): Response<ApiResponse<ReservaResponse>>

    @PUT("reservas/{id}")
    suspend fun atualizarReserva(@Path("id") id: String, @Body request: ReservaRequest): Response<ApiResponse<ReservaResponse>>

    @PUT("reservas/{id}/cancelar")
    suspend fun cancelarReserva(@Path("id") id: String): Response<ApiResponse<Unit>>

    // Confirmar reserva
    @POST("reservas/{id}/confirmar")
    suspend fun confirmarReserva(@Path("id") id: String): Response<ApiResponse<ReservaResponse>>

    // Verificar disponibilidade
    @POST("reservas/verificar-disponibilidade")
    suspend fun verificarDisponibilidade(
        @Body request: VerificarDisponibilidadeResponse
    ): Response<ApiResponse<DisponibilidadeResponse>>

    // Obter QR Code da reserva
    @GET("reservas/{id}/qr-code")
    suspend fun getQrCodeReserva(@Path("id") id: String): Response<ApiResponse<String>>

    // Estatísticas de reservas
    @GET("reservas/estatisticas/{usuario_id}")
    suspend fun getEstatisticasReservas(
        @Path("usuario_id") usuarioId: String
    ): Response<ApiResponse<Map<String, Any>>>

    // Histórico de reservas
    @GET("reservas/historico/{usuario_id}")
    suspend fun getHistoricoReservas(
        @Path("usuario_id") usuarioId: String,
        @Query("mes") mes: Int,
        @Query("ano") ano: Int
    ): Response<ApiResponse<PaginatedResponse<ReservaResponse>>>

}