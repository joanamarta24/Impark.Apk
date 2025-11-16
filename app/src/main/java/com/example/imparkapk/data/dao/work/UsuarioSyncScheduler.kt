package com.example.imparkapk.data.dao.work

import android.content.Context
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

object UsuarioSyncScheduler {

    fun enqueueNow(context: Context) {
        val req = OneTimeWorkRequestBuilder<UsuarioSyncWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        WorkManager.getInstance(context).enqueue(req)
    }

    fun enqueuePeriodic(context: Context) {
        val periodicRequest = androidx.work.PeriodicWorkRequestBuilder<UsuarioSyncWorker>(
            java.time.Duration.ofHours(4) // Sincronizar a cada 4 horas
        )
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        WorkManager.getInstance(context).enqueue(periodicRequest)
    }

    fun enqueueWithDelay(context: Context, delayMinutes: Long) {
        val req = OneTimeWorkRequestBuilder<UsuarioSyncWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .setInitialDelay(delayMinutes, java.util.concurrent.TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(context).enqueue(req)
    }

    fun cancelAll(context: Context) {
        WorkManager.getInstance(context).cancelAllWork()
    }

    fun enqueueExpedited(context: Context) {
        val req = OneTimeWorkRequestBuilder<UsuarioSyncWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .setExpedited(androidx.work.OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .build()

        WorkManager.getInstance(context).enqueue(req)
    }
}