package com.nikol.domain.repository

import com.nikol.domain.state.ArticlesState

interface ArticlesRepository {
    suspend fun getAllArticles(): ArticlesState
}