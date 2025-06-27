package com.nikol.domain.state

import com.nikol.domain.model.Account

/**
 * Представляет состояние получения списка аккаунтов.
 */
sealed class AccountState {

    /**
     * Успешное получение списка аккаунтов.
     * @param items список аккаунтов
     */
    data class Success(val items: List<Account>) : AccountState()

    /**
     * Ошибка при получении аккаунтов.
     * @param message сообщение об ошибке
     */
    data class Error(val message: String) : AccountState()

    /**
     * Отсутствует подключение к интернету.
     */
    data object NoInternet : AccountState()
}

