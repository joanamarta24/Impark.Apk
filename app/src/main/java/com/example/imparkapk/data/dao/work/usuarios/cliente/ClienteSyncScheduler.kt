package com.example.imparkapk.data.dao.work.usuarios.cliente

import android.content.Context
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

object ClienteSyncScheduler {
    fun enqueueNow(context: Context) {
        val req = OneTimeWorkRequestBuilder<ClienteSyncWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()
        WorkManager.getInstance(context).enqueue(req)
    }
}