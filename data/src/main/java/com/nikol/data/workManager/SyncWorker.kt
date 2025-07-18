package com.nikol.data.workManager

import android.content.Context
import android.util.Log
import androidx.work.Configuration
import androidx.work.CoroutineWorker
import androidx.work.DelegatingWorkerFactory
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.nikol.data.sync.SyncOrchestrator
import javax.inject.Inject
import javax.inject.Singleton

class SyncWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val orchestrator: SyncOrchestrator
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        Log.d("SyncWorker", "STARTED")
        return try {
            orchestrator.syncAll()
            Log.d("SyncWorker", "COMPLETED")
            Result.success()
        } catch (e: Exception) {
            Log.e("SyncWorker", "ERROR: ${e.message}", e)
            Result.retry()
        }
    }
}

class SyncWorkerFactory(
    private val syncOrchestrator: SyncOrchestrator
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            SyncWorker::class.java.name ->
                SyncWorker(appContext, workerParameters, syncOrchestrator)

            else -> null
        }
    }
}
@Singleton
class AppWorkerFactory @Inject constructor(
    syncOrchestrator: SyncOrchestrator
) : DelegatingWorkerFactory() {
    init {
        addFactory(SyncWorkerFactory(syncOrchestrator))
    }
}
