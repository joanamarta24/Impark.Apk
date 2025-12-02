package com.example.imparkapk.data.dao.work.usuarios.usuario

import android.content.Context
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.imparkapk.data.dao.work.usuarios.usuario.UsuarioSyncWorker
import java.time.Duration
import java.util.concurrent.TimeUnit

object UsuarioSyncScheduler {

    fun enqueueNow(context: Context) {
        val req = OneTimeWorkRequestBuilder<UsuarioSyncWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        WorkManager.Companion.getInstance(context).enqueue(req)
    }

    fun enqueuePeriodic(context: Context) {
        val periodicRequest = PeriodicWorkRequestBuilder<UsuarioSyncWorker>(
            Duration.ofHours(4) // Sincronizar a cada 4 horas
        )
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        WorkManager.Companion.getInstance(context).enqueue(periodicRequest)
    }

    fun enqueueWithDelay(context: Context, delayMinutes: Long) {
        val req = OneTimeWorkRequestBuilder<UsuarioSyncWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .setInitialDelay(delayMinutes, TimeUnit.MINUTES)
            .build()

        WorkManager.Companion.getInstance(context).enqueue(req)
    }

    fun cancelAll(context: Context) {
        WorkManager.Companion.getInstance(context).cancelAllWork()
    }

    fun enqueueExpedited(context: Context) {
        val req = OneTimeWorkRequestBuilder<UsuarioSyncWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .build()

        WorkManager.Companion.getInstance(context).enqueue(req)
    }
}