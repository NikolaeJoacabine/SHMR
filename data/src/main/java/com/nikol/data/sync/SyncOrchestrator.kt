package com.nikol.data.sync

import android.util.Log
import javax.inject.Inject

class SyncOrchestrator @Inject constructor(
    private val repositories: Set<@JvmSuppressWildcards SyncableRepository>
) {
    suspend fun syncAll() {
        repositories
            .sortedBy { it.order }
            .forEach { it.sync() }
    }
}
