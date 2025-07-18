package com.nikol.data.articles.local

import com.nikol.data.articles.local.database.ArticleEntity
import com.nikol.domain.model.Articles

interface LocalArticlesRepository {
    suspend fun getAllArticles(): List<ArticleEntity>
    suspend fun upsertAll(articles: List<ArticleEntity>)
}
