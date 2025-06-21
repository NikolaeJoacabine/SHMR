package com.nikol.data.transaction.remote

import com.nikol.data.network.FinanceAPI
import com.nikol.data.network.NetworkStatusProvider
import com.nikol.data.util.mapper.toDomain
import com.nikol.data.util.retryOnServerError
import com.nikol.domain.state.TransactionState
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class RemoteTransactionRepositoryImpl(
    private val financeAPI: FinanceAPI,
    val networkStatusProvider: NetworkStatusProvider
) :
    RemoteTransactionRepository {


    override suspend fun transactionForThePeriod(): TransactionState {
        if (!networkStatusProvider.isConnected()) {
            return TransactionState.NetworkError
        }

        return runCatching {
            retryOnServerError { financeAPI.getTransactionBeginningOfTheMonth() }
        }
            .mapCatching { response ->
                if (response.isSuccessful && response.body() != null) {
                    TransactionState.Success(response.body()!!.map { it.toDomain() })
                } else {
                    TransactionState.Error("ERROR")
                }
            }.getOrElse {
                TransactionState.Error("ERROR")
            }
    }

    override suspend fun transactionForToday(): TransactionState {
        if (!networkStatusProvider.isConnected()) {
            return TransactionState.NetworkError
        }
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val today = LocalDate.now(ZoneOffset.UTC).format(formatter)

        return runCatching {
            retryOnServerError {
                financeAPI.getTransactionForPeriod(
                    startDate = today,
                    endDate = today
                )
            }
        }
            .mapCatching { response ->
                if (response.isSuccessful && response.body() != null) {
                    TransactionState.Success(response.body()!!.map { it.toDomain() })
                } else {
                    TransactionState.Error("ERROR")
                }
            }.getOrElse {
                TransactionState.Error("ERROR")
            }
    }

    override suspend fun getTransactionsByPeriod(
        startDate: LocalDate,
        endDate: LocalDate
    ): TransactionState {
        if (!networkStatusProvider.isConnected()) {
            return TransactionState.NetworkError
        }
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val startDateStr = startDate.format(formatter)
        val endDateStr = endDate.format(formatter)

        return runCatching {
            retryOnServerError {
                financeAPI.getTransactionForPeriod(
                    startDate = startDateStr,
                    endDate = endDateStr
                )
            }
        }.mapCatching { response ->
            if (response.isSuccessful && response.body() != null) {
                TransactionState.Success(response.body()!!.map { it.toDomain() })
            } else {
                TransactionState.Error("ERROR")
            }
        }.getOrElse {
            TransactionState.Error("ERROR")
        }
    }
}