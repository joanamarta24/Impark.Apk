package com.example.imparkapk.data.remote.api

import com.example.imparkapk.data.remote.dto.auth.LoginRequest
import com.example.imparkapk.data.remote.dto.auth.MeResponse
import com.example.imparkapk.data.remote.dto.auth.RefreshRequest
import com.example.imparkapk.data.remote.dto.auth.TokenResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApiService {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): TokenResponse

    @GET("auth/me")
    suspend fun me(): MeResponse

    @POST("auth/refresh")
    suspend fun refresh(@Body body: RefreshRequest): TokenResponse

    @POST("auth/refresh")
    fun refreshCall(@Body body: RefreshRequest): Call<TokenResponse>
}
