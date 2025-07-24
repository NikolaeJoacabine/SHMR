package com.nikol.data.workManager

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.nikol.settings.SettingsManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

object SyncWorkerScheduler {

    private const val WORK_NAME = "PeriodicSyncWorker"

    fun scheduleSync(
        context: Context,
        settingsManager: SettingsManager,
        shouldReplace: Boolean = true
    ) {
        CoroutineScope(Dispatchers.Default).launch {
            val interval = settingsManager.syncInterval.first()
            val workManager = WorkManager.getInstance(context)

            val workRequest = PeriodicWorkRequestBuilder<SyncWorker>(
                repeatInterval = interval.toLong(),
                repeatIntervalTimeUnit = TimeUnit.HOURS
            )
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                )
                .setBackoffCriteria(
                    BackoffPolicy.EXPONENTIAL,
                    30, TimeUnit.SECONDS
                )
                .build()

            val policy = if (shouldReplace) {
                ExistingPeriodicWorkPolicy.UPDATE
            } else {
                ExistingPeriodicWorkPolicy.KEEP
            }

            workManager.enqueueUniquePeriodicWork(
                WORK_NAME,
                policy,
                workRequest
            )
        }
    }
}
