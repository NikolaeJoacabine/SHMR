package com.nikol.yandexschool.features.articles.screnns.articles.state_hoisting

import com.nikol.domain.model.Articles

sealed class ArticlesScreenState {
    data object Error : ArticlesScreenState()
    data object Loading : ArticlesScreenState()
    data class Content(val list: List<Articles>) : ArticlesScreenState()
    data object Empty : ArticlesScreenState()
}