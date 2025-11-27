package com.example.imparkapk.di

import android.content.Context
import com.example.imparkapk.data.local.TokenStore
import com.example.imparkapk.data.remote.api.AuthApiService
import com.example.imparkapk.data.remote.api.AcessoApi
import com.example.imparkapk.data.remote.api.AvaliacaoApi
import com.example.imparkapk.data.remote.api.CarroApi
import com.example.imparkapk.data.remote.api.EstacionamentoApi
import com.example.imparkapk.data.remote.api.ReservaApi
import com.example.imparkapk.data.remote.api.usuarios.ClienteApi
import com.example.imparkapk.data.remote.api.usuarios.DonoApi
import com.example.imparkapk.data.remote.api.usuarios.GerenteApi
import com.example.imparkapk.data.remote.dto.auth.RefreshRequest
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
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    val baseUrl: String = "http://10.0.2.2:8080/"

    @Provides
    @Singleton
    @Named("baseUrl")
    fun provideBaseUrl(): String = baseUrl

    @Provides
    @Singleton
    fun provideRetroFit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideLogging(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenStore: TokenStore): Interceptor = Interceptor { chain ->
        val original = chain.request()
        val token = tokenStore.token
        val newRequest =
            original.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        chain.proceed(newRequest)
    }

    @Provides
    @Singleton
    fun provideTokenAuthenticator(
        tokenStore: TokenStore,
        @Named("auth") authRetrofit: Retrofit
    ): Authenticator = Authenticator { _, response ->
        if (response.request.url.encodedPath.contains("/auth/refresh") || responseCount(response) >= 2)
            return@Authenticator null

        val refresh = tokenStore.token
        val service = authRetrofit.create(AuthApiService::class.java)

        android.util.Log.d("Auth", "Tentando refresh do token...")

        val refreshed = try {
            val resp = service.refreshCall(RefreshRequest(refresh)).execute()
            if (resp.isSuccessful) resp.body() else null
        } catch (_: Exception) { null }

        val newAccess = refreshed?.accessToken ?: return@Authenticator null

        kotlinx.coroutines.runBlocking {
            tokenStore.saveToken(newAccess)
            refreshed.refreshToken?.let { tokenStore.saveToken(it) }
        }

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
    fun provideAuthClient(logging: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    @Named("secureClient")
    fun provideSecureClient(
        logging: HttpLoggingInterceptor,
        authInterceptor: Interceptor,
        authenticator: Authenticator
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(authInterceptor)
            .authenticator(authenticator)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
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
    @Named("authApi")
    fun provideAuthApi(@Named("auth") retrofit: Retrofit): AuthApiService =
        retrofit.create(AuthApiService::class.java)

    @Provides
    @Singleton
    fun provideClienteApi(@Named("secure") retrofit: Retrofit): ClienteApi =
        retrofit.create(ClienteApi::class.java)

    @Provides
    @Singleton
    fun provideDonoApi(@Named("secure") retrofit: Retrofit): DonoApi =
        retrofit.create(DonoApi::class.java)

    @Provides
    @Singleton
    fun provideGerenteApi(@Named("secure") retrofit: Retrofit): GerenteApi =
        retrofit.create(GerenteApi::class.java)

    @Provides
    @Singleton
    fun provideReservaApi(@Named("secure") retrofit: Retrofit): ReservaApi =
        retrofit.create(ReservaApi::class.java)

    @Provides
    @Singleton
    fun providesCarroApi(@Named("secure") retrofit: Retrofit): CarroApi =
        retrofit.create(CarroApi::class.java)

    @Provides
    @Singleton
    fun providesEstacionamentoApi(@Named("secure") retrofit: Retrofit): EstacionamentoApi =
        retrofit.create(EstacionamentoApi::class.java)

    @Provides
    @Singleton
    fun providesAcessoApi(@Named("secure") retrofit: Retrofit): AcessoApi =
        retrofit.create(AcessoApi::class.java)

    @Provides
    @Singleton
    fun providesAvaliacaoApi(@Named("secure") retrofit: Retrofit): AvaliacaoApi =
        retrofit.create(AvaliacaoApi::class.java)

    @Provides
    @Singleton
    fun provideTokenStore(@ApplicationContext context: Context): TokenStore = TokenStore(context)

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()
}