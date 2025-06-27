package com.nikol.domain.repository

import com.nikol.domain.state.AccountIdState
import com.nikol.domain.state.AccountState

/**
 * Интерфейс репозитория для работы с аккаунтом.
 * Определяет методы для получения информации об аккаунте и текущего ID аккаунта.
 */
interface AccountRepository {

    /**
     * Получить данные аккаунта из источника (например, сети или кэша).
     *
     * @return состояние аккаунта [AccountState], включающее данные или ошибку.
     */
    suspend fun getAccount(): AccountState

    /**
     * Получить текущий ID аккаунта.
     * Обычно сначала пытается получить локально сохранённый ID,
     * а если его нет, загружает с сервера и сохраняет локально.
     *
     * @return состояние с ID аккаунта [AccountIdState] или ошибкой.
     */
    suspend fun getCurrentAccountId(): AccountIdState
}
