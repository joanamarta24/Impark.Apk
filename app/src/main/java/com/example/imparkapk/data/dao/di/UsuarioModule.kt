package com.example.imparkapk.data.dao.di

import com.example.imparkapk.data.dao.converters.menager.UsuarioStateManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UsuarioModule {
    @Provides
    @Singleton
    fun provideUsuarioStateManager(): UsuarioStateManager{
        return UsuarioStateManager()
    }
}