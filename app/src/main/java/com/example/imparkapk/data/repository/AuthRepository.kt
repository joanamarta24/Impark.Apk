package com.example.imparktcc.repository

import com.example.imparkapk.data.local.TokenStore
import com.example.imparkapk.data.remote.AuthApiService
import com.example.imparkapk.data.remote.LoginRequest
import com.example.imparkapk.data.remote.MeResponse
import com.example.imparkapk.data.remote.RefreshRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
@Singleton
class AuthRepository @Inject constructor(
    @Named("authApi") private val authApi: AuthApiService,
    @Named("secureApi") private val secureApi: AuthApiService,
    private val store: TokenStore
) {

    suspend fun login(email: String, senha: String): Boolean {
        val resp = authApi.login(LoginRequest(email, senha))
        store.saveTokens(resp.accessToken, resp.refreshToken)

        return true
    }

    suspend fun bootstrapSession(): MeResponse? {
        return if (store.isRefreshValidNow()) {
            store.getMeCached()
        } else null
    }

    suspend fun ensureFreshAccess(): Boolean {
        if (store.isAccessValidNow()) return true
        val refresh = store.getRefreshToken()
        if (refresh.isNullOrBlank()) return false
        if (!store.isRefreshValidNow()) return false
        return runCatching {
            val rt = authApi.refresh(RefreshRequest(refresh))
            store.saveTokens(rt.accessToken, rt.refreshToken ?: refresh)
            true
        }.getOrDefault(false)
    }

    suspend fun logout() = store.clearTokens()

    fun tokenFlow(): Flow<String?> = store.token

    suspend fun ensureFreshAccessLocallyOnly(): Boolean {
        return store.isRefreshValidNow()
    }

}
