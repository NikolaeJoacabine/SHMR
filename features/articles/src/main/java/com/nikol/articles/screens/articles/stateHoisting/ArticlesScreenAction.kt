package com.nikol.articles.screens.articles.stateHoisting

/**
 * Действия (Actions) пользователя на экране статей.
 */
sealed class ArticlesScreenAction {
    /** Событие входа на экран (инициализация). */
    data object OnScreenEntered : ArticlesScreenAction()

    /** Событие изменения поискового запроса. */
    data class OnSearchQueryChanged(val query: String) : ArticlesScreenAction()

    /** Событие нажатия кнопки повтора загрузки после ошибки. */
    data object OnClickToRetry : ArticlesScreenAction()
}

