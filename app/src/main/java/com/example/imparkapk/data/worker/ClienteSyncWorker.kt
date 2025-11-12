package com.example.imparkapk.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.imparkapk.data.repository.usuarios.ClienteRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class ClienteSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: ClienteRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        android.util.Log.i("UsuarioSyncWorker", "Executando sincronização...")
        return try {
            repository.sincronizarclientes()

            android.util.Log.i("UsuarioSyncWorker", "Sincronização concluída com sucesso.")
            Result.success()
        } catch (e: Exception) {
            android.util.Log.e("UsuarioSyncWorker", "Erro na sincronização", e)
            Result.retry()
        }

    }

}