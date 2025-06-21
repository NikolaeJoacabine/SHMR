package com.nikol.data.articles

import com.nikol.data.articles.remote.RemoteArticlesRepository
import com.nikol.domain.repository.ArticlesRepository
import com.nikol.domain.state.ArticlesState

class ArticlesRepositoryImpl(private val remoteArticlesRepository: RemoteArticlesRepository) :
    ArticlesRepository {
    override suspend fun getAllArticles(): ArticlesState {
        return remoteArticlesRepository.getArticles()
    }
}