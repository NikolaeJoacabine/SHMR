package com.nikol.data.articles.remote

import com.nikol.domain.state.ArticlesState

interface RemoteArticlesRepository {
    suspend fun getArticles(): ArticlesState
}