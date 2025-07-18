package com.nikol.data.sync

interface SyncableRepository {
    val order: Int
    suspend fun sync()
}
