package com.nikol.data.articles.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface ArticlesDao {

    @Query("SELECT * FROM articles")
    suspend fun getAllArticles(): List<ArticleEntity>

    @Update
    suspend fun updateArticles(articles: List<ArticleEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArticles(articles: List<ArticleEntity>): List<Long>

    @Transaction
    suspend fun upsertAll(articles: List<ArticleEntity>) {
        val insertResult = insertArticles(articles)
        val toUpdate = articles.mapIndexedNotNull { index, article ->
            if (insertResult[index] == -1L) article else null
        }
        if (toUpdate.isNotEmpty()) updateArticles(toUpdate)
    }

    @Query("SELECT * FROM articles WHERE article_id = :id")
    suspend fun getArticleById(id: Int): ArticleEntity
}
