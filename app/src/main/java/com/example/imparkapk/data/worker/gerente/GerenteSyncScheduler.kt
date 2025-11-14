package com.example.imparkapk.data.worker.gerente

import android.content.Context
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

object GerenteSyncScheduler {
    fun enqueueNow(context: Context) {
        val req = OneTimeWorkRequestBuilder<GerenteSyncWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()
        WorkManager.getInstance(context).enqueue(req)
    }
}
