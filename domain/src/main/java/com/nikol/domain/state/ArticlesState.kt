package com.nikol.domain.state

import com.nikol.domain.model.Articles

sealed class ArticlesState {
    object Loading : ArticlesState()
    data class Success(val items: List<Articles>) : ArticlesState()
    data class Error(val message: String) : ArticlesState()
}