package com.nikol.data.articles.local

import com.nikol.data.articles.local.database.ArticleEntity
import com.nikol.data.articles.local.database.ArticlesDao
import jakarta.inject.Inject

class LocalArticlesRepositoryImpl @Inject constructor(
    private val dao: ArticlesDao
) : LocalArticlesRepository {

    override suspend fun getAllArticles(): List<ArticleEntity> {
        return dao.getAllArticles()
    }

    override suspend fun upsertAll(articles: List<ArticleEntity>) {
        dao.upsertAll(articles)
    }
}