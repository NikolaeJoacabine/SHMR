package com.nikol.domain.useCase

import com.nikol.domain.repository.ArticlesRepository
import com.nikol.domain.state.ArticlesState

/**
 * Use case для получения списка статей.
 *
 * Делегирует вызов репозиторию статей и возвращает состояние статей
 * (успех с данными, ошибка или отсутствие интернета).
 *
 * @return [ArticlesState] — текущее состояние получения статей.
 */
class GetArticlesUseCase(private val articlesRepository: ArticlesRepository) {

    suspend operator fun invoke(): ArticlesState {
        return articlesRepository.getAllArticles()
    }
}
