package com.example.imparkapk.data.dao.ui.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.imparkapk.data.dao.ui.viewmodel.AvaliacaoViewModel
import com.example.imparkapk.data.dao.ui.viewmodel.CarroViewModel
import com.example.imparkapk.data.dao.ui.viewmodel.ClienteViewModel
import com.example.imparktcc.ui.viewmodel.ReservaViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.internal.lifecycle.HiltViewModelMap
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @HiltViewModelMap
    abstract fun bindUsuarioViewModel(clienteViewModel: ClienteViewModel): ViewModel

    @Binds
    @IntoMap
    @HiltViewModelMap
    abstract fun bindCarroViewModel(carroViewModel: CarroViewModel): ViewModel

    @Binds
    @IntoMap
    @HiltViewModelMap
    abstract fun bindReservaViewModel(reservaViewModel: ReservaViewModel): ViewModel

    @Binds
    @IntoMap
    @HiltViewModelMap
    abstract fun bindAvaliacaoViewModel(avaliacaoViewModel: AvaliacaoViewModel): ViewModel

    //ClienteViewModel
    @Binds
    @IntoMap
    @HiltViewModelMap
    abstract fun bindClienteViewModel(clienteViewModel: ClienteViewModel): ViewModel

}

@Module
@InstallIn(SingletonComponent::class)
object ViewModelFactoryModule {
    @Provides
    @Singleton
    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ViewModelProvider.NewInstanceFactory()
    }
}