package com.example.imparkapk.data.remote

import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


// DTOs
data class LoginRequest(val email: String, val senha: String)
data class TokenResponse(val accessToken: String, val refreshToken: String?)
data class RefreshRequest(val refreshToken: Flow<String?>)
data class MeResponse(val nome: String, val email: String)

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
