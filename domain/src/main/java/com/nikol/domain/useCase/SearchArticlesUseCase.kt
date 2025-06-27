package com.nikol.domain.useCase

import com.nikol.domain.model.Articles

/**
 * Use case для поиска статей по заданному запросу.
 *
 * Фильтрует переданный список статей по имени, игнорируя регистр,
 * возвращая только те статьи, имя которых содержит поисковый запрос.
 * Если запрос пустой или содержит только пробелы, возвращает исходный список без изменений.
 */
class SearchArticlesUseCase {

    /**
     * Фильтрует список статей по запросу.
     *
     * @param list список статей для поиска.
     * @param query поисковый запрос.
     * @return отфильтрованный список статей, имя которых содержит запрос.
     */
    fun searchArticles(list: List<Articles>, query: String): List<Articles> {
        if (query.isBlank()) return list

        return list.filter {
            it.name.contains(query.trim(), ignoreCase = true)
        }
    }
}
