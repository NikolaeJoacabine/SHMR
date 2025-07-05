package com.nikol.data.account.remote

import com.nikol.data.network.FinanceAPI
import com.nikol.data.network.NetworkStatusProvider
import com.nikol.data.util.mapper.toDTO
import com.nikol.data.util.mapper.toDomain
import com.nikol.data.util.retryOnServerError
import com.nikol.domain.model.AccountUpdateRequest
import com.nikol.domain.state.AccountByIdState
import com.nikol.domain.state.AccountDeleteState
import com.nikol.domain.state.AccountEditState
import com.nikol.domain.state.AccountState
import retrofit2.HttpException

/**
 * Реализация [RemoteAccountRepository], которая получает данные аккаунта
 * с удалённого сервера через [FinanceAPI].
 *
 * Использует [NetworkStatusProvider] для проверки подключения к сети
 * перед выполнением запроса.
 *
 * Обрабатывает ошибки сети и серверные ошибки, возвращая соответствующие состояния.
 *
 * @property financeAPI API для взаимодействия с удалённым сервером.
 * @property networkStatusProvider провайдер состояния сети.
 */
class RemoteAccountRepositoryImpl(
    private val financeAPI: FinanceAPI,
    private val networkStatusProvider: NetworkStatusProvider
) : RemoteAccountRepository {

    /**
     * Получает данные аккаунта с удалённого сервера.
     * Если отсутствует подключение к интернету, возвращает [AccountState.NoInternet].
     * В случае успешного ответа возвращает [AccountState.Success] с данными,
     * иначе — [AccountState.Error].
     */
    override suspend fun getAccount(): AccountState {
        if (!networkStatusProvider.isConnected()) {
            return AccountState.NoInternet
        }

        return runCatching { retryOnServerError { financeAPI.getAccount() } }
            .mapCatching { response ->
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    AccountState.Success(body.map { it.toDomain() })
                } else {
                    AccountState.Error("")
                }
            }
            .getOrElse {
                AccountState.Error("")
            }
    }

    override suspend fun getAccountById(id: Int): AccountByIdState {
        if (!networkStatusProvider.isConnected()) {
            return AccountByIdState.NoInternet
        }

        return runCatching { retryOnServerError { financeAPI.getAccountById(id) } }
            .mapCatching { response ->
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    AccountByIdState.Success(body.toDomain())
                } else {
                    AccountByIdState.Error("")
                }
            }
            .getOrElse {
                AccountByIdState.Error("")
            }
    }

    override suspend fun editAccount(
        accountUpdateRequest: AccountUpdateRequest,
        id: Int
    ): AccountEditState {
        if (!networkStatusProvider.isConnected()) {
            return AccountEditState.NoInternet
        }

        return runCatching {
            retryOnServerError {
                financeAPI.editAccount(
                    id = id,
                    accountUpdateRequest = accountUpdateRequest.toDTO()
                )
            }
        }
            .mapCatching { response ->
                if (response.isSuccessful) {
                    AccountEditState.Success
                } else {
                    AccountEditState.Error
                }
            }
            .getOrElse {
                AccountEditState.Error
            }

    }

    override suspend fun deleteAccount(id: Int): AccountDeleteState {
        if (!networkStatusProvider.isConnected()) {
            return AccountDeleteState.NoInternet
        }

        return runCatching {
            retryOnServerError { financeAPI.deleteAccount(id) }
        }.map { response ->
            when {
                response.isSuccessful -> AccountDeleteState.Success
                response.code() == 409 -> AccountDeleteState.Conflict
                else -> AccountDeleteState.Error
            }
        }.getOrElse {
            AccountDeleteState.Error
        }
    }

}
