package com.example.imparkapk.data.dao.remote.api.api

import com.example.imparkapk.data.dao.remote.api.request.BuscarEstacionamentosRequest
import com.example.imparkapk.data.dao.remote.api.response.ApiResponse
import com.example.imparkapk.data.dao.remote.api.response.EstacionamentoResponse
import com.example.imparkapk.data.dao.remote.api.response.PaginatedResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface EstacionamentoApi {
    @GET("estacionamento")
    suspend fun listarEStacionamento(
        @Query("page") page:Int = 1,
        @Query("linit") limit: Int = 20
    ):Response<ApiResponse<PaginatedResponse<EstacionamentoResponse>>>
    @POST("estacionamentos/buscar")
    suspend fun buscarEstacionamentos(@Body request: BuscarEstacionamentosRequest):Response
}