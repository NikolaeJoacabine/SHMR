package com.nikol.domain.useCase

import com.nikol.domain.repository.ArticlesRepository
import com.nikol.domain.state.ArticlesState

class GetArticlesUseCase(private val articlesRepository: ArticlesRepository) {

    suspend operator fun invoke(): ArticlesState {
        return articlesRepository.getAllArticles()
    }
}