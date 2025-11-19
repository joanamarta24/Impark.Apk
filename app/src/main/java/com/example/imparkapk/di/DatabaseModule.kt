package com.example.imparkapk.di

import android.content.Context
import androidx.room.Room
import com.example.imparkapk.data.local.dao.AcessoDao
import com.example.imparkapk.data.local.dao.AvaliacaoDao
import com.example.imparkapk.data.local.dao.CarroDao
import com.example.imparkapk.data.local.dao.EstacionamentoDao
import com.example.imparkapk.data.local.dao.ReservaDao
import com.example.imparkapk.data.local.dao.usuarios.ClienteDao
import com.example.imparkapk.data.local.dao.usuarios.DonoDao
import com.example.imparkapk.data.local.dao.usuarios.GerenteDao
import com.example.imparkapk.data.local.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "usuarios.db"
        ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideClienteDao(db: AppDatabase): ClienteDao = db.clienteDao()

    @Provides
    @Singleton
    fun provideGerenteDao(db: AppDatabase): GerenteDao = db.gerenteDao()

    @Provides
    @Singleton
    fun provideDonoDao(db: AppDatabase): DonoDao = db.donoDao()

    @Provides
    @Singleton
    fun provideEstacionamentoDao(db: AppDatabase): EstacionamentoDao = db.estacionamentoDao()

    @Provides
    @Singleton
    fun provideReservaDao(db: AppDatabase): ReservaDao = db.reservaDao()

    @Provides
    @Singleton
    fun provideAvaliacaoDao(db: AppDatabase): AvaliacaoDao = db.avaliacaoDao()

    @Provides
    @Singleton
    fun provideCarroDao(db: AppDatabase): CarroDao = db.carroDao()


    @Provides
    @Singleton
    fun provideAcessoDao(db: AppDatabase): AcessoDao = db.acessoDao()
}
