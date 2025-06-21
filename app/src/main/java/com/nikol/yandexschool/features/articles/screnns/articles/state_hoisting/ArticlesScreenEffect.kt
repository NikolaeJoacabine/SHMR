package com.nikol.yandexschool.features.articles.screnns.articles.state_hoisting

sealed class ArticlesScreenEffect {
    data object ScrollToTop : ArticlesScreenEffect()
}