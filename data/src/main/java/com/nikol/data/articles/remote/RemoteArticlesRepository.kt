package com.nikol.data.articles.remote

import com.nikol.domain.state.ArticlesState

/**
 * Репозиторий для получения статей из удалённого источника (например, API).
 */
interface RemoteArticlesRepository {

    /**
     * Загружает список статей из удалённого источника.
     *
     * @return состояние загрузки статей [ArticlesState], включая успешный результат или ошибку.
     */
    suspend fun getArticles(): ArticlesState
}
