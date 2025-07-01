package com.nikol.articles.screens.articles.stateHoisting

import com.nikol.domain.model.Articles

/**
 * Состояния экрана статей.
 */
sealed class ArticlesScreenState {

    /** Состояние ошибки при загрузке статей. */
    data object Error : ArticlesScreenState()

    /** Состояние загрузки данных. */
    data object Loading : ArticlesScreenState()

    /** Состояние с отображением списка статей. */
    data class Content(val list: List<Articles>) : ArticlesScreenState()

    /** Состояние пустого списка статей (нет данных). */
    data object Empty : ArticlesScreenState()

    /** Состояние отсутствия интернет-соединения. */
    data object NoInternet : ArticlesScreenState()
}
