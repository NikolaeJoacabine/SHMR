package com.nikol.domain.state

/**
 * Состояние получения текущего ID аккаунта.
 */
sealed class AccountIdState {

    /**
     * Успешное получение ID аккаунта.
     * @param id идентификатор аккаунта
     */
    data class Success(val id: Int) : AccountIdState()

    /**
     * Ошибка при получении ID аккаунта.
     */
    data object Error : AccountIdState()

    /**
     * Нет подключения к интернету при попытке получить ID аккаунта.
     */
    data object NoInternet : AccountIdState()
}

