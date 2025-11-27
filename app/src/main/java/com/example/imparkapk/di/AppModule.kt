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