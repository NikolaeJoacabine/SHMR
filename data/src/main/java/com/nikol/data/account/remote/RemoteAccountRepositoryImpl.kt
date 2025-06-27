package com.nikol.data.account.remote

import com.nikol.data.network.FinanceAPI
import com.nikol.data.network.NetworkStatusProvider
import com.nikol.data.util.mapper.toDomain
import com.nikol.data.util.retryOnServerError
import com.nikol.domain.state.AccountState

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
}
