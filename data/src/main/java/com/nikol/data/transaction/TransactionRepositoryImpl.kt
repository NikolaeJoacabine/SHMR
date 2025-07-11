package com.nikol.data.transaction

import com.nikol.data.transaction.remote.RemoteTransactionRepository
import com.nikol.data.util.mapper.toData
import com.nikol.domain.model.CreateTransaction
import com.nikol.domain.model.UpdateTransaction
import com.nikol.domain.repository.TransactionRepository
import com.nikol.domain.state.CreateTransactionState
import com.nikol.domain.state.DeleteTransactionState
import com.nikol.domain.state.DetailTransactionState
import com.nikol.domain.state.TransactionState
import com.nikol.domain.state.UpdateTransactionState
import java.time.LocalDate

/**
 * Реализация [TransactionRepository], которая получает транзакции
 * через удалённый репозиторий [RemoteTransactionRepository].
 *
 * Делегирует запросы к удалённому источнику данных и предоставляет
 * интерфейс для получения транзакций за сегодняшний день и за период.
 *
 * @property remoteTransactionRepository Репозиторий для получения данных транзакций из сети.
 */
class TransactionRepositoryImpl(
    private val remoteTransactionRepository: RemoteTransactionRepository
) : TransactionRepository {

    /**
     * Получает список транзакций за сегодняшний день для указанного аккаунта.
     *
     * @param id Идентификатор аккаунта, для которого запрашиваются транзакции.
     * @return [TransactionState] — состояние с данными транзакций или ошибкой.
     */
    override suspend fun getTransactionsForToday(id: Int): TransactionState {
        return remoteTransactionRepository.transactionForToday(id)
    }

    /**
     * Получает список транзакций за указанный период для заданного аккаунта.
     *
     * @param id Идентификатор аккаунта, для которого запрашиваются транзакции.
     * @param startDate Начальная дата периода (включительно).
     * @param endDate Конечная дата периода (включительно).
     * @return [TransactionState] — состояние с данными транзакций или ошибкой.
     */
    override suspend fun getTransactionsByPeriod(
        id: Int,
        startDate: LocalDate,
        endDate: LocalDate
    ): TransactionState {
        return remoteTransactionRepository.getTransactionsByPeriod(id, startDate, endDate)
    }

    override suspend fun createTransaction(createTransaction: CreateTransaction): CreateTransactionState {
        return remoteTransactionRepository.createTransaction(createTransaction.toData())
    }

    override suspend fun getDetailTransaction(id: Int): DetailTransactionState {
        return remoteTransactionRepository.getTransactionById(id)
    }

    override suspend fun updateTransaction(
        id: Int,
        updateTransaction: UpdateTransaction
    ): UpdateTransactionState {
        return remoteTransactionRepository.updateTransaction(id, updateTransaction.toData())
    }

    override suspend fun deleteTransaction(id: Int): DeleteTransactionState {
        return remoteTransactionRepository.deleteTransaction(id)
    }
}
