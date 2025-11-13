package com.example.imparkapk.data.dao.remote.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

//DTOS
data class LoginRequest(val email:String, val senha:String)
data class TokenResponse(val accessToken:String, val refheshToken:String)
data class RefreshRequest(val refheshToken: String)
data class MeResponse(val nome:String, val email: String)

interface AuthApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest):TokenResponse

    @GET("auth/me")
    suspend fun me():MeResponse

    @POST("auth/me")
    suspend fun refresh(@Body body:RefreshRequest):TokenResponse

    @POST("auth/refresh")
    fun refrehCall(@Body body: RefreshRequest): Call<TokenResponse>
}