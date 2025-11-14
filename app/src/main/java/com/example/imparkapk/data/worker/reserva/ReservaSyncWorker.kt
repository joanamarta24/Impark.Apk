package com.example.imparkapk.data.worker.reserva

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.rafaelcosta.modelo_app_crud_usuario_api.data.repository.ReservaRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class ReservaSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: ReservaRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        Log.i("UsuarioSyncWorker", "Executando sincronização...")
        return try {
            repository.sincronizarUsuarios()

            Log.i("UsuarioSyncWorker", "Sincronização concluída com sucesso.")
            Result.success()
        } catch (e: Exception) {
            Log.e("UsuarioSyncWorker", "Erro na sincronização", e)
            Result.retry()
        }
    }
}