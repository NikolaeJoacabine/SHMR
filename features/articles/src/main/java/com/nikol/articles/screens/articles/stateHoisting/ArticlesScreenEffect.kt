package com.nikol.articles.screens.articles.stateHoisting

/**
 * Побочные эффекты (Side Effects) экрана статей.
 */
sealed class ArticlesScreenEffect {
    /** Эффект прокрутки списка статей к началу. */
    data object ScrollToTop : ArticlesScreenEffect()
}
