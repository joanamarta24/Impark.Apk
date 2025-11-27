package com.example.imparkapk.data.dao.di

import java.util.concurrent.TimeUnit
import com.example.imparkapk.data.dao.remote.api.api.ClienteCarroApi
import com.example.imparkapk.data.dao.remote.api.api.EstacionamentosApi
import com.example.imparkapk.data.dao.remote.api.api.usuarios.GerenteApi
import com.example.imparkapk.data.dao.remote.api.api.ReservaApi
import com.example.imparkapk.data.dao.remote.api.api.usuarios.ClienteApi
import com.google.firebase.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
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
    fun provideUsuarioApi(retrofit: Retrofit): ClienteApi = retrofit.create()

    @Provides
    @Singleton
    fun provideCarroApi(retrofit: Retrofit): ClienteCarroApi = retrofit.create()

    @Provides
    @Singleton
    fun provideEstacionamentoApi(retrofit: Retrofit): EstacionamentosApi = retrofit.create()

    @Provides
    @Singleton
    fun provideReservaApi(retrofit: Retrofit): ReservaApi = retrofit.create()

    @Provides
    @Singleton
    fun  provideGerenteApi(retrofit: Retrofit): GerenteApi = retrofit.create()


}
private inline fun <reified T> Retrofit.create(): T = create(T::class.java)
object NewtworkModelu{
    @Provides
    @Singleton
    fun  provideEstacionamentoApi(retrofit: Retrofit): EstacionamentosApi{
        return retrofit.create(EstacionamentosApi::class.java)
    }
    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.impark.com/v1/") // URL base da API
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
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
    fun provideGson(): Gson{
        return GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .create()
    }

}