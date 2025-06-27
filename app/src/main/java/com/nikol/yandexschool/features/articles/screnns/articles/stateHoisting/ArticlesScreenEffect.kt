package com.nikol.yandexschool.features.articles.screnns.articles.stateHoisting

/**
 * Побочные эффекты (Side Effects) экрана статей.
 */
sealed class ArticlesScreenEffect {
    /** Эффект прокрутки списка статей к началу. */
    data object ScrollToTop : ArticlesScreenEffect()
}
