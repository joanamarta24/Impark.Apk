package com.example.imparkapk.data.worker.estacionamento

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.rafaelcosta.modelo_app_crud_usuario_api.data.repository.EstacionamentoRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class EstacionamentoSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: EstacionamentoRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        Log.i("UsuarioSyncWorker", "Executando sincronização...")
        return try {
            repository.sincronizarEstacionamentos()

            Log.i("UsuarioSyncWorker", "Sincronização concluída com sucesso.")
            Result.success()
        } catch (e: Exception) {
            Log.e("UsuarioSyncWorker", "Erro na sincronização", e)
            Result.retry()
        }

    }
}