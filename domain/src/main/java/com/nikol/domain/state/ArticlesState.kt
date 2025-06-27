package com.nikol.domain.state

import com.nikol.domain.model.Articles

/**
 * Представляет состояние получения списка статей.
 */
sealed class ArticlesState {

    /**
     * Успешное получение списка статей.
     * @param items список статей
     */
    data class Success(val items: List<Articles>) : ArticlesState()

    /**
     * Ошибка при получении статей.
     * @param message сообщение об ошибке
     */
    data class Error(val message: String) : ArticlesState()

    /**
     * Ошибка сети (например, отсутствует интернет).
     */
    data object NetworkError : ArticlesState()
}
