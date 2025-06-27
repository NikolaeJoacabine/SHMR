package com.nikol.data.account.remote

import com.nikol.domain.state.AccountState

/**
 * Репозиторий для получения информации об аккаунте из удалённого источника.
 */
interface RemoteAccountRepository {

    /**
     * Получает состояние аккаунта из удалённого источника.
     *
     * @return [AccountState] — текущее состояние аккаунта, например, успех, ошибка или отсутствие интернета.
     */
    suspend fun getAccount(): AccountState
}
