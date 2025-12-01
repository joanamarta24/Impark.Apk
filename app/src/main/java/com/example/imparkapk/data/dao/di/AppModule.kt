package com.example.imparkapk.di

import android.content.Context
import com.example.imparkapk.data.dao.local.dao.TokenStore
import com.example.imparkapk.data.dao.remote.api.AuthApiService
import com.example.imparkapk.data.dao.remote.api.RefreshRequest
import com.example.imparkapk.data.dao.remote.api.api.usuarios.ClienteApi
import com.google.firebase.BuildConfig
import java.util.concurrent.TimeUnit
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton
import kotlinx.coroutines.runBlocking // Import adicionado

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @Named("baseUrl")
    fun provideBaseUrl(): String = "http://10.0.2.2:8080/"

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenStore: TokenStore): Interceptor = Interceptor { chain ->
        val original = chain.request()
        val token = runBlocking {
            tokenStore.getAccessToken()
        }
        val newRequest = if (!token.isNullOrBlank()) {
            original.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            original
        }
        chain.proceed(newRequest)
    }

    @Provides
    @Singleton
    fun provideTokenAuthenticator(
        tokenStore: TokenStore,
        @Named("auth") authRetrofit: Retrofit
    ): Authenticator = Authenticator { _, response ->
        if (response.request.url.encodedPath.contains("/auth/refresh") ||
            responseCount(response) >= 2) {
            return@Authenticator null
        }

        val refresh = tokenStore.getRefreshTokenSync() ?: return@Authenticator null
        val service = authRetrofit.create(AuthApiService::class.java)

        android.util.Log.d("Auth", "Tentando refresh do token...")

        val refreshed = try {
            val resp = service.refreshCall(RefreshRequest(refresh)).execute()
            if (resp.isSuccessful) resp.body() else null
        } catch (e: Exception) {
            android.util.Log.e("Auth", "Erro no refresh: ${e.message}")
            null
        }

        val newAccess = refreshed?.accessToken ?: return@Authenticator null

        // Usar métodos síncronos
        tokenStore.saveRefreshTokenSync(newAccess)
        refreshed.refreshToken?.let { tokenStore.saveRefreshTokenSync(it) }

        response.request.newBuilder()
            .header("Authorization", "Bearer $newAccess")
            .build()
    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var prior = response.priorResponse
        while (prior != null) {
            count++
            prior = prior.priorResponse
        }
        return count
    }

    @Provides
    @Singleton
    @Named("authClient")
    fun provideAuthClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    @Named("secureClient")
    fun provideSecureClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: Interceptor,
        authenticator: Authenticator
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .authenticator(authenticator)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    @Named("auth")
    fun provideAuthRetrofit(
        @Named("baseUrl") baseUrl: String,
        @Named("authClient") client: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    @Named("secure")
    fun provideSecureRetrofit(
        @Named("baseUrl") baseUrl: String,
        @Named("secureClient") client: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideAuthApiService(@Named("auth") retrofit: Retrofit): AuthApiService =
        retrofit.create(AuthApiService::class.java)

    @Provides
    @Singleton
    fun provideClienteApi(@Named("secure") retrofit: Retrofit): ClienteApi =
        retrofit.create(ClienteApi::class.java)

    @Provides
    @Singleton
    fun provideTokenStore(
        @ApplicationContext context: Context,
        gson: Gson
    ): TokenStore = TokenStore(context, gson)

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

}