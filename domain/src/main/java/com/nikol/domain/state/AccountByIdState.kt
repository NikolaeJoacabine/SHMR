package com.nikol.domain.state

import com.nikol.domain.model.Account

/**
 * Представляет состояние получения счёта.
 */
sealed class AccountByIdState {

    /**
     * Успешное получение аккаунта.
     * @param item модель счёта
     */
    data class Success(val item: Account) : AccountByIdState()

    /**
     * Ошибка при получении аккаунтов.
     * @param message сообщение об ошибке
     */
    data class Error(val message: String) : AccountByIdState()

    /**
     * Отсутствует подключение к интернету.
     */
    data object NoInternet : AccountByIdState()

}
