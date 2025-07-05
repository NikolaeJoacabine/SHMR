package com.nikol.data.account.remote

import com.nikol.domain.model.AccountUpdateRequest
import com.nikol.domain.state.AccountByIdState
import com.nikol.domain.state.AccountDeleteState
import com.nikol.domain.state.AccountEditState
import com.nikol.domain.state.AccountIdState
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

    /**
     * Получает состояние аккаунта из удалённого источника.
     *
     * @param id id счёта пользователя
     * @return [AccountIdState] — текущее состояние аккаунта, например, успех, ошибка или отсутствие интернета.
     */
    suspend fun getAccountById(id: Int): AccountByIdState

    suspend fun editAccount(
        accountUpdateRequest: AccountUpdateRequest,
        id: Int
    ): AccountEditState

    suspend fun deleteAccount(id: Int): AccountDeleteState
}
