package com.example.imparkapk.data.worker.acesso

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.imparkapk.data.repository.AcessoRepository
import com.rafaelcosta.modelo_app_crud_usuario_api.data.repository.CarroRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class AcessoSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: AcessoRepository
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