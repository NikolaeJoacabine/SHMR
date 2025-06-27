package com.nikol.data.articles

import com.nikol.data.articles.remote.RemoteArticlesRepository
import com.nikol.domain.repository.ArticlesRepository
import com.nikol.domain.state.ArticlesState

/**
 * Реализация [ArticlesRepository], которая использует удалённый источник статей.
 *
 * Делегирует вызов получения статей в [RemoteArticlesRepository].
 */
class ArticlesRepositoryImpl(
    private val remoteArticlesRepository: RemoteArticlesRepository
) : ArticlesRepository {

    /**
     * Получить все статьи из удалённого источника.
     *
     * @return состояние загрузки статей [ArticlesState].
     */
    override suspend fun getAllArticles(): ArticlesState {
        return remoteArticlesRepository.getArticles()
    }
}
