package com.nikol.data.account

import com.nikol.data.account.local.LocalAccountRepository
import com.nikol.data.account.remote.RemoteAccountRepository
import com.nikol.domain.repository.AccountRepository
import com.nikol.domain.state.AccountIdState
import com.nikol.domain.state.AccountState

/**
 * Репозиторий аккаунта, который объединяет работу с удалённым и локальным хранилищами данных.
 *
 * Использует [RemoteAccountRepository] для получения данных аккаунта с сервера
 * и [LocalAccountRepository] для сохранения и получения текущего ID аккаунта из локального хранилища.
 *
 * @property remoteAccountRepository источник данных аккаунта из сети.
 * @property localAccountRepository локальное хранилище для ID аккаунта.
 */
class AccountRepositoryImpl(
    private val remoteAccountRepository: RemoteAccountRepository,
    private val localAccountRepository: LocalAccountRepository
) : AccountRepository {

    /**
     * Получает данные аккаунта из удалённого репозитория.
     * @return [AccountState] с состоянием загрузки аккаунта.
     */
    override suspend fun getAccount(): AccountState {
        return remoteAccountRepository.getAccount()
    }

    /**
     * Получает текущий ID аккаунта.
     * Если ID уже сохранён локально, возвращает его.
     * Иначе получает аккаунт с удалённого репозитория, сохраняет первый ID локально и возвращает.
     *
     * @return [AccountIdState] с состоянием получения ID аккаунта.
     */
    override suspend fun getCurrentAccountId(): AccountIdState {
        val id = localAccountRepository.getCurrentAccountId()
        if (id != null) {
            return AccountIdState.Success(id)
        } else {
            val account = remoteAccountRepository.getAccount()
            val result = when (account) {
                is AccountState.Error -> AccountIdState.Error
                is AccountState.NoInternet -> AccountIdState.NoInternet
                is AccountState.Success -> {
                    localAccountRepository.saveCurrentAccountId(account.items.first().id)
                    AccountIdState.Success(account.items.first().id)
                }
            }
            return result
        }
    }
}
