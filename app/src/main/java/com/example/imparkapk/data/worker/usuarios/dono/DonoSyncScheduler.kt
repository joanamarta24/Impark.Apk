package com.example.imparkapk.data.worker.usuarios.dono

import android.content.Context
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

object DonoSyncScheduler {
    fun enqueueNow(context: Context) {
        val req = OneTimeWorkRequestBuilder<DonoSyncWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()
        WorkManager.getInstance(context).enqueue(req)
    }
}