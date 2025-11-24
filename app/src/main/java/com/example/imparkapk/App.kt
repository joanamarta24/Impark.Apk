package com.example.imparkapk

import android.app.Application
import android.widget.Toast
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.imparkapk.data.network.AcessoNetworkMonitor
import com.example.imparkapk.data.network.AvaliacaoNetworkMonitor
import com.example.imparkapk.data.network.CarroNetworkMonitor
import com.example.imparkapk.data.network.ClienteNetworkMonitor
import com.example.imparkapk.data.network.DonoNetworkMonitor
import com.example.imparkapk.data.network.EstacionamentoNetworkMonitor
import com.example.imparkapk.data.network.GerenteNetworkMonitor
import com.example.imparkapk.data.network.ReservaNetworkMonitor
import com.example.imparkapk.data.worker.usuarios.cliente.ClienteSyncWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()

    override fun onCreate() {
        super.onCreate()

        try {
            WorkManager.initialize(this, workManagerConfiguration)
            android.util.Log.i("App", "WorkManager inicializado manualmente com HiltWorkerFactory")
        } catch (e: IllegalStateException) {
            android.util.Log.i("App", "WorkManager já inicializado: ${e.message}")
        }

        ClienteNetworkMonitor.register(this)
        GerenteNetworkMonitor.register(this)
        DonoNetworkMonitor.register(this)

        EstacionamentoNetworkMonitor.register(this)
        AvaliacaoNetworkMonitor.register(this)
        AcessoNetworkMonitor.register(this)
        CarroNetworkMonitor.register(this)
        ReservaNetworkMonitor.register(this)

        agendarSincronizacaoPeriodica()

    }

    private fun agendarSincronizacaoPeriodica() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val periodicWork = PeriodicWorkRequestBuilder<ClienteSyncWorker>(
            15, TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "usuario_sync_periodica",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWork
        )

        Toast.makeText(this, "Sincronização agendada (a cada 15 min).", Toast.LENGTH_SHORT).show()
    }
}