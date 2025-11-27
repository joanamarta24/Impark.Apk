package com.example.imparkapk.data.repository

import com.example.imparkapk.data.local.TokenStore
import com.example.imparkapk.data.remote.api.api.AuthApiService
import com.example.imparkapk.data.remote.dto.auth.LoginRequest
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: AuthApiService,
    private val store: TokenStore
) {

    suspend fun login(email: String, senha: String): Boolean {
        val token = api.login(LoginRequest(email, senha)).accessToken
        store.saveToken(token)
        return token.isNotBlank()
    }

    suspend fun me() = api.me()

    suspend fun logout() = store.clearToken()

    fun tokenFlow() = store.token
}