package com.example.imparkapk.data.dao.di

import android.icu.util.TimeUnit
import com.example.imparkapk.data.dao.remote.api.api.CarroApi
import com.example.imparkapk.data.dao.remote.api.api.EstacionamentoApi
import com.example.imparkapk.data.dao.remote.api.api.GerenteApi
import com.example.imparkapk.data.dao.remote.api.api.ReservaApi
import com.example.imparkapk.data.dao.remote.api.api.UsuarioApi
import com.google.firebase.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton


@Module
@Inject(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https//api.impark.com/v1/"
    private const val TIMEOUT_SECONDS = 30

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                // Adicione headers de autenticação se necessário
                // .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient{
        val loggingInterceptor = HttpLoggingInterceptor().apply{
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor (loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .build()
                chain.proceed(request)
            }
            .build()
    }
     @Provides
     @Singleton
     fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit{
         return Retrofit.Builder()
             .build(BASE_URL)
             .client(okHttpClient)
             .addConverterFactory(GsonConverterFactory.create())
     }
    @Provides
    @Singleton
    fun provideUsuarioApi(retrofit: Retrofit): UsuarioApi{
        return retrofit.create(UsuarioApi::class.java)
    }
    @Provides
    @Singleton
    fun provideCarroApi(retrofit: Retrofit): CarroApi{
        return retrofit.create(CarroApi::class.java)
    }
    @Provides
    @Singleton
    fun provideEstacionamentoApi(retrofit: Retrofit): EstacionamentoApi {
        return retrofit.create(EstacionamentoApi::class.java)
    }
    @Provides
    @Singleton
    fun provideReservaApi(retrofit: Retrofit): ReservaApi {
        return retrofit.create(ReservaApi::class.java)
    }
    @Provides
    @Singleton
    fun provideGerenteApi(retrofit: Retrofit): GerenteApi{
        return retrofit.create(GerenteApi::class.java)
    }
}