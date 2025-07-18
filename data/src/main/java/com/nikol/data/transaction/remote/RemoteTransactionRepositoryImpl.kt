package com.nikol.data.transaction.remote

import com.nikol.data.model.CreateTransactionDTO
import com.nikol.data.model.TransactionDTO
import com.nikol.data.network.FinanceAPI
import com.nikol.data.network.NetworkStatusProvider
import com.nikol.data.util.formater.toApiFormat
import com.nikol.data.util.mapper.toDomain
import com.nikol.data.util.mapper.toDomainDetail
import com.nikol.data.util.retryOnServerError
import com.nikol.domain.state.ArticlesState
import com.nikol.domain.state.CreateTransactionState
import com.nikol.domain.state.DeleteTransactionState
import com.nikol.domain.state.DetailTransactionState
import com.nikol.domain.state.TransactionState
import com.nikol.domain.state.UpdateTransactionState
import retrofit2.Response
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import javax.inject.Inject

/**
 * Реализация [RemoteTransactionRepository] для получения транзакций
 * с удалённого API через [FinanceAPI] с учётом состояния сети.
 *
 * @property financeAPI API для работы с финансами.
 * @property networkStatusProvider Провайдер статуса сети.
 */
class RemoteTransactionRepositoryImpl @Inject constructor(
    private val financeAPI: FinanceAPI,
    private val networkStatusProvider: NetworkStatusProvider
) : RemoteTransactionRepository {

    /**
     * Получает транзакции за сегодняшний день для указанного аккаунта.
     *
     * @param id Идентификатор аккаунта.
     * @return Состояние результата загрузки транзакций.
     */
    override suspend fun transactionForToday(id: Int): TransactionState {
        val today = LocalDate.now(ZoneOffset.UTC)
        return getTransactionsWithCheck {
            financeAPI.getTransactionForPeriod(
                accountId = id,
                startDate = today.toApiFormat(),
                endDate = today.toApiFormat()
            )
        }
    }

    override suspend fun getAllTransactions(): TransactionState {
        return getTransactionsWithCheck {
            financeAPI.getTransactionForPeriod(
                accountId = 218,
                startDate = LocalDate.now().withDayOfYear(1).toApiFormat(),
                endDate = LocalDate.now().toApiFormat()
            )
        }
    }

    /**
     * Получает транзакции за произвольный период для указанного аккаунта.
     *
     * @param id Идентификатор аккаунта.
     * @param startDate Начальная дата периода.
     * @param endDate Конечная дата периода.
     * @return Состояние результата загрузки транзакций.
     */
    override suspend fun getTransactionsByPeriod(
        id: Int,
        startDate: LocalDate,
        endDate: LocalDate
    ): TransactionState {
        return getTransactionsWithCheck {
            financeAPI.getTransactionForPeriod(
                accountId = id,
                startDate = startDate.toApiFormat(),
                endDate = endDate.toApiFormat()
            )
        }
    }

    override suspend fun createTransaction(createTransactionDTO: CreateTransactionDTO): CreateTransactionState {
        if (!networkStatusProvider.isConnected()) {
            return CreateTransactionState.NetworkError
        }

        return runCatching {
            retryOnServerError {
                financeAPI.createTransaction(createTransactionDTO)
            }
        }
            .mapCatching { response ->
                when (response.code()) {
                    201 -> CreateTransactionState.Success(response.body()!!.id)
                    404 -> CreateTransactionState.NotFound
                    else -> CreateTransactionState.Error
                }
            }.getOrElse {
                CreateTransactionState.Error
            }
    }

    override suspend fun getTransactionById(id: Int): DetailTransactionState {
        if (!networkStatusProvider.isConnected()) {
            return DetailTransactionState.NoInternet
        }

        return runCatching {
            retryOnServerError {
                financeAPI.getTransactionById(id)
            }
        }
            .mapCatching { response ->
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    DetailTransactionState.Success(body.toDomainDetail())
                } else {
                    DetailTransactionState.Error
                }
            }
            .getOrElse {
                DetailTransactionState.Error
            }
    }

    override suspend fun updateTransaction(
        id: Int,
        createTransactionDTO: CreateTransactionDTO
    ): UpdateTransactionState {
        if (!networkStatusProvider.isConnected()) {
            return UpdateTransactionState.NoInternet
        }
        return runCatching {
            retryOnServerError {
                financeAPI.updateTransaction(id, createTransactionDTO)
            }
        }
            .mapCatching { response ->
                when (response.code()) {
                    200 -> UpdateTransactionState.Success
                    404 -> UpdateTransactionState.NotFound
                    else -> UpdateTransactionState.Error
                }
            }
            .getOrElse {
                UpdateTransactionState.Error
            }
    }

    override suspend fun deleteTransaction(id: Int): DeleteTransactionState {
        if (!networkStatusProvider.isConnected()) {
            return DeleteTransactionState.NoInternet
        }

        return runCatching {
            retryOnServerError {
                financeAPI.deleteTransaction(id)
            }
        }.mapCatching { response ->
            when (response.code()) {
                204 -> DeleteTransactionState.Success
                404 -> DeleteTransactionState.NotFound
                else -> DeleteTransactionState.Error
            }
        }.getOrElse {
            DeleteTransactionState.Error
        }
    }


    /**
     * Вспомогательный метод для выполнения API вызова с проверкой подключения к сети,
     * повторными попытками при ошибках сервера и обработкой результата.
     *
     * @param apiCall Функция, выполняющая сетевой запрос.
     * @return Состояние результата загрузки транзакций.
     */
    private suspend fun getTransactionsWithCheck(
        apiCall: suspend () -> Response<List<TransactionDTO>>
    ): TransactionState {
        if (!networkStatusProvider.isConnected()) {
            return TransactionState.NetworkError
        }

        return runCatching {
            retryOnServerError { apiCall() }
        }.mapCatching { response ->
            val body = response.body()
            if (response.isSuccessful && body != null) {
                TransactionState.Success(body.map { it.toDomain() })
            } else {
                TransactionState.Error("Ошибка получения данных")
            }
        }.getOrElse {
            TransactionState.Error(it.localizedMessage ?: "Неизвестная ошибка")
        }
    }
}
