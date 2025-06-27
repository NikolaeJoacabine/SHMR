package com.nikol.data.transaction.remote

import com.nikol.data.model.TransactionDTO
import com.nikol.data.network.FinanceAPI
import com.nikol.data.network.NetworkStatusProvider
import com.nikol.data.util.formater.toApiFormat
import com.nikol.data.util.mapper.toDomain
import com.nikol.data.util.retryOnServerError
import com.nikol.domain.state.TransactionState
import retrofit2.Response
import java.time.LocalDate
import java.time.ZoneOffset

/**
 * Реализация [RemoteTransactionRepository] для получения транзакций
 * с удалённого API через [FinanceAPI] с учётом состояния сети.
 *
 * @property financeAPI API для работы с финансами.
 * @property networkStatusProvider Провайдер статуса сети.
 */
class RemoteTransactionRepositoryImpl(
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
