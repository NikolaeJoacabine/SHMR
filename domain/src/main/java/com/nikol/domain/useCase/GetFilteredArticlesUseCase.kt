package com.nikol.domain.useCase

import com.nikol.domain.model.TransactionType
import com.nikol.domain.repository.ArticlesRepository
import com.nikol.domain.state.ArticlesState

class GetFilteredArticlesUseCase(
    private val articlesRepository: ArticlesRepository
) {
    suspend operator fun invoke(transactionType: TransactionType): ArticlesState {
        return when (val response = articlesRepository.getAllArticles()) {
            is ArticlesState.Success -> {
                ArticlesState.Success(
                    response.items.filter {
                        it.isIncome == (transactionType == TransactionType.Income)
                    }
                )
            }

            is ArticlesState.Error -> ArticlesState.Error("")
            is ArticlesState.NetworkError -> ArticlesState.NetworkError
        }
    }
}
