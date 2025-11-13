package com.example.imparkapk.data.dao.di

import java.util.concurrent.TimeUnit
import com.example.imparkapk.data.dao.remote.api.api.CarroApi
import com.example.imparkapk.data.dao.remote.api.api.EstacionamentoApi
import com.example.imparkapk.data.dao.remote.api.api.usuarios.GerenteApi
import com.example.imparkapk.data.dao.remote.api.api.ReservaApi
import com.example.imparkapk.data.dao.remote.api.api.usuarios.UsuarioApi
import com.google.firebase.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://api.impark.com/v1/"
    private const val TIMEOUT_SECONDS = 30L


    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.BASIC
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
                .addHeader("User-Agent", "ImparkApp/${BuildConfig.VERSION_NAME}")
                .build()
            chain.proceed(request)
        }
    }
    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: Interceptor
    ): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Providers das APIs

    @Provides
    @Singleton
    fun provideUsuarioApi(retrofit: Retrofit): UsuarioApi = retrofit.create()

    @Provides
    @Singleton
    fun provideCarroApi(retrofit: Retrofit): CarroApi = retrofit.create()

    @Provides
    @Singleton
    fun provideEstacionamentoApi(retrofit: Retrofit): EstacionamentoApi = retrofit.create()

    @Provides
    @Singleton
    fun provideReservaApi(retrofit: Retrofit): ReservaApi = retrofit.create()

    @Provides
    @Singleton
    fun  provideGerenteApi(retrofit: Retrofit): GerenteApi = retrofit.create()


}
private inline fun <reified T> Retrofit.create(): T = create(T::class.java)