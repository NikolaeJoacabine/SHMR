package com.nikol.data.account

import com.nikol.data.account.local.LocalAccountRepository
import com.nikol.data.account.remote.RemoteAccountRepository
import com.nikol.data.network.NetworkStatusProvider
import com.nikol.data.sync.SyncableRepository
import com.nikol.data.util.mapper.toDomain
import com.nikol.data.util.mapper.toEntity
import com.nikol.domain.model.AccountUpdateRequest
import com.nikol.domain.model.CurrencyType
import com.nikol.domain.repository.AccountRepository
import com.nikol.domain.state.AccountByIdState
import com.nikol.domain.state.AccountDeleteState
import com.nikol.domain.state.AccountEditState
import com.nikol.domain.state.AccountIdState
import com.nikol.domain.state.AccountState
import com.nikol.domain.state.ArticlesState
import javax.inject.Inject

/**
 * Репозиторий аккаунта, который объединяет работу с удалённым и локальным хранилищами данных.
 *
 * Использует [RemoteAccountRepository] для получения данных аккаунта с сервера
 * и [LocalAccountRepository] для сохранения и получения текущего ID аккаунта из локального хранилища.
 *
 * @property remoteAccountRepository источник данных аккаунта из сети.
 * @property localAccountRepository локальное хранилище для ID аккаунта.
 */
class AccountRepositoryImpl @Inject constructor(
    private val remoteAccountRepository: RemoteAccountRepository,
    private val localAccountRepository: LocalAccountRepository,
    private val networkStatusProvider: NetworkStatusProvider
) : AccountRepository, SyncableRepository {


    override val order: Int
        get() = 2

    override suspend fun sync() {
        if (!networkStatusProvider.isConnected()) return

        val unsyncedAccounts = localAccountRepository.getUnsyncedAccounts()
        unsyncedAccounts.forEach { account ->
            val result = remoteAccountRepository.editAccount(
                AccountUpdateRequest(
                    name = account.name,
                    balance = account.balance.toDouble(),
                ),
                account.accountId
            )

            if (result is AccountEditState.Success) {
                localAccountRepository.updateAccount(
                    account.copy(
                        isSynced = true,
                        lastSyncedAt = System.currentTimeMillis()
                    )
                )
            }
        }

        val remoteResult = remoteAccountRepository.getAccount()
        if (remoteResult is AccountState.Success) {
            val syncedEntities = remoteResult.items.map {
                it.toEntity(
                    isSynced = true,
                    lastSyncedAt = System.currentTimeMillis()
                )
            }
            localAccountRepository.upsertAll(syncedEntities)
        }
    }


    /**
     * Получает данные аккаунта из удалённого репозитория.
     * @return [AccountState] с состоянием загрузки аккаунта.
     */
    override suspend fun getAccount(): AccountState {
        sync()

        val localData = localAccountRepository.getAllAccounts()
        return if (localData.isEmpty()) {
            if (networkStatusProvider.isConnected()) {
                AccountState.Error("")
            } else {
                AccountState.NoInternet
            }
        } else {
            AccountState.Success(localData.map { it.toDomain() })
        }
    }

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

    override suspend fun getAccountById(id: Int): AccountByIdState {
        sync()
        val account = localAccountRepository.getAccountById(id)
        return if (account != null) {
            AccountByIdState.Success(account.toDomain())
        } else {
            AccountByIdState.Error("")
        }
    }

    override suspend fun getCurrentCurrency(): CurrencyType {
        val res = localAccountRepository.getCurrentCurrency()
        return when (res) {
            "RUB" -> CurrencyType.RUB
            "EUR" -> CurrencyType.EUR
            "USD" -> CurrencyType.USD
            else -> CurrencyType.RUB
        }
    }

    override suspend fun saveCurrency(currencyType: CurrencyType) {
        localAccountRepository.saveCurrency(currencyType.type)
    }

    override suspend fun editAccount(
        accountUpdateRequest: AccountUpdateRequest,
        id: Int
    ): AccountEditState {
        val localAccount = localAccountRepository.getAccountById(id)
        if (localAccount == null) return AccountEditState.Error

        val updated = localAccount.copy(
            name = accountUpdateRequest.name,
            balance = accountUpdateRequest.balance.toInt().toString(),
            isSynced = false,
            lastSyncedAt = null
        )

        localAccountRepository.updateAccount(updated)

        return if (networkStatusProvider.isConnected()) {
            when (val result = remoteAccountRepository.editAccount(accountUpdateRequest, id)) {
                is AccountEditState.Success -> {
                    localAccountRepository.updateAccount(
                        updated.copy(
                            isSynced = true,
                            lastSyncedAt = System.currentTimeMillis()
                        )
                    )
                    AccountEditState.Success
                }
                else -> result
            }
        } else {
            AccountEditState.Success
        }
    }

    override suspend fun deleteAccount(id: Int): AccountDeleteState {
        val transactionCount = localAccountRepository.getTransactionCountForAccount(id)
        if (transactionCount > 0) {
            return AccountDeleteState.Conflict
        }

        return if (networkStatusProvider.isConnected()) {
            when (val result = remoteAccountRepository.deleteAccount(id)) {
                is AccountDeleteState.Success -> {
                    localAccountRepository.deleteAccount(id)
                    AccountDeleteState.Success
                }
                else -> result
            }
        } else {
            val deleted = localAccountRepository.deleteAccount(id)
            if (deleted) {
                localAccountRepository.saveDeletedAccountId(id)
                AccountDeleteState.NoInternet
            } else {
                AccountDeleteState.Error
            }
        }
    }
}
