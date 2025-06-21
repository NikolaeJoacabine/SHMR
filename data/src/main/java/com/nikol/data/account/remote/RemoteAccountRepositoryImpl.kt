package com.nikol.data.account.remote

import com.nikol.data.network.FinanceAPI
import com.nikol.data.util.mapper.toDomain
import com.nikol.data.util.retryOnServerError
import com.nikol.domain.state.AccountState

class RemoteAccountRepositoryImpl(
    private val financeAPI: FinanceAPI
) : RemoteAccountRepository {
    override suspend fun getAccount(): AccountState {
        return runCatching {
            retryOnServerError {
                financeAPI.getAccount()
            }
        }.mapCatching { response ->
            if (response.isSuccessful && response.body() != null) {
                AccountState.Success(response.body()!!.map { it.toDomain() })
            } else {
                AccountState.Error("")
            }
        }.getOrElse { e ->
            AccountState.Error("")
        }
    }
}