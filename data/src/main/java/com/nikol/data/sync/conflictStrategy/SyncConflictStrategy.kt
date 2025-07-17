package com.nikol.data.sync.conflictStrategy

interface SyncConflictStrategy<T> {
    fun resolveConflict(local: T, remote: T): T
}
