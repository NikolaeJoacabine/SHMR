package com.nikol.yandexschool.features.articles.screnns.articles.state_hoisting

sealed class ArticlesScreenAction {
    data object OnScreenEntered : ArticlesScreenAction()
    data class OnSearchQueryChanged(val query: String) : ArticlesScreenAction()
}