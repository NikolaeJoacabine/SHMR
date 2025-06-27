package com.nikol.domain.repository

import com.nikol.domain.state.ArticlesState

/**
 * Репозиторий для получения статей.
 */
interface ArticlesRepository {
    /**
     * Получить все статьи.
     *
     * @return состояние загрузки статей [ArticlesState].
     */
    suspend fun getAllArticles(): ArticlesState
}
