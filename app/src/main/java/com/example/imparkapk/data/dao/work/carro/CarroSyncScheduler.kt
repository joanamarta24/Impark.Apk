package com.example.imparkapk.data.dao.work.carro

import android.content.Context
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

object CarroSyncScheduler {
    fun enqueueNow(context: Context) {
        val req = OneTimeWorkRequestBuilder<CarroSyncWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()
        WorkManager.getInstance(context).enqueue(req)
    }
}