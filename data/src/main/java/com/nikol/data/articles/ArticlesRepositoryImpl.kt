package com.nikol.data.articles

import android.util.Log
import com.nikol.data.articles.local.LocalArticlesRepository
import com.nikol.data.articles.remote.RemoteArticlesRepository
import com.nikol.data.network.NetworkStatusProvider
import com.nikol.data.sync.SyncableRepository
import com.nikol.data.util.mapper.toDomain
import com.nikol.data.util.mapper.toDto
import com.nikol.data.util.mapper.toEntity
import com.nikol.domain.repository.ArticlesRepository
import com.nikol.domain.state.ArticlesState
import javax.inject.Inject

/**
 * Реализация [ArticlesRepository], которая использует удалённый источник статей.
 *
 * Делегирует вызов получения статей в [RemoteArticlesRepository].
 */
class ArticlesRepositoryImpl @Inject constructor(
    private val remoteArticlesRepository: RemoteArticlesRepository,
    private val localArticlesRepository: LocalArticlesRepository,
    private val networkStatusProvider: NetworkStatusProvider
) : ArticlesRepository, SyncableRepository {

    override val order: Int get() = 1

    override suspend fun getAllArticles(): ArticlesState {
        sync()

        val local = localArticlesRepository.getAllArticles()
        return if (local.isEmpty()) {
            ArticlesState.NetworkError
        } else {
            ArticlesState.Success(local.map { it.toDomain() })
        }
    }

    override suspend fun sync() {
        if (!networkStatusProvider.isConnected()) return

        when (val result = remoteArticlesRepository.getArticles()) {
            is ArticlesState.Success -> {
                val entities = result.items.map {
                    it.toEntity(
                        isSynced = true,
                        lastSyncedAt = System.currentTimeMillis()
                    )
                }
                localArticlesRepository.upsertAll(entities)
            }
            else -> Unit
        }
    }
}

