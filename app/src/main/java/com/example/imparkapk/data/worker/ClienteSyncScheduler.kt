package com.example.imparkapk.data.worker

import android.content.Context

object ClienteSyncScheduler {
    fun enqueueNow(context: Context) {
        val req = androidx.work.OneTimeWorkRequestBuilder<ClienteSyncWorker>()
            .setConstraints(
                androidx.work.Constraints.Builder()
                    .setRequiredNetworkType(androidx.work.NetworkType.CONNECTED)
                    .build()
            )
            .build()
        androidx.work.WorkManager.getInstance(context).enqueue(req)
    }
}
