package com.nikol.data.transaction

import android.util.Log
import com.nikol.data.network.NetworkStatusProvider
import com.nikol.data.sync.SyncableRepository
import com.nikol.data.sync.conflictStrategy.SyncConflictStrategy
import com.nikol.data.transaction.local.LocalTransactionRepository
import com.nikol.data.transaction.local.database.TransactionEntity
import com.nikol.data.transaction.remote.RemoteTransactionRepository
import com.nikol.data.util.generateLocalId
import com.nikol.data.util.mapper.toData
import com.nikol.data.util.mapper.toDto
import com.nikol.data.util.mapper.toEntity
import com.nikol.data.util.sanitizeComment
import com.nikol.domain.model.CreateTransaction
import com.nikol.domain.model.UpdateTransaction
import com.nikol.domain.repository.TransactionRepository
import com.nikol.domain.state.CreateTransactionState
import com.nikol.domain.state.DeleteTransactionState
import com.nikol.domain.state.DetailTransactionState
import com.nikol.domain.state.TransactionState
import com.nikol.domain.state.UpdateTransactionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import javax.inject.Inject

/**
 * Реализация [TransactionRepository], которая получает транзакции
 * через удалённый репозиторий [RemoteTransactionRepository].
 *
 * Делегирует запросы к удалённому источнику данных и предоставляет
 * интерфейс для получения транзакций за сегодняшний день и за период.
 *
 * @property remoteTransactionRepository Репозиторий для получения данных транзакций из сети.
 */
class TransactionRepositoryImpl @Inject constructor(
    private val remoteTransactionRepository: RemoteTransactionRepository,
    private val localTransactionRepository: LocalTransactionRepository,
    private val networkStatusProvider: NetworkStatusProvider,
    private val conflictStrategy: SyncConflictStrategy<TransactionEntity>,
) : TransactionRepository, SyncableRepository {

    /**
     * Получает список транзакций за сегодняшний день для указанного аккаунта.
     *
     * @param id Идентификатор аккаунта, для которого запрашиваются транзакции.
     * @return [TransactionState] — состояние с данными транзакций или ошибкой.
     */
    override suspend fun getTransactionsForToday(id: Int): TransactionState {
        if (localTransactionRepository.hasUnsynced()) {
            sync()
        }

        return if (networkStatusProvider.isConnected()) {
            val response = remoteTransactionRepository.transactionForToday(id)
            if (response is TransactionState.Success) {
                localTransactionRepository.insertAll(response.items.map {
                    it.toEntity(
                        isSynced = true,
                        lastSyncedAt = System.currentTimeMillis()
                    )
                })
            }
            response
        } else {
            val result = localTransactionRepository.transactionForToday(id)
            TransactionState.Success(result)
        }
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
        if (localTransactionRepository.hasUnsynced()) {
            sync()
        }

        return if (networkStatusProvider.isConnected()) {
            val response = remoteTransactionRepository.getTransactionsByPeriod(id, startDate, endDate)
            if (response is TransactionState.Success) {
                localTransactionRepository.insertAll(response.items.map {
                    it.toEntity(
                        isSynced = true,
                        lastSyncedAt = System.currentTimeMillis()
                    )
                })
            }
            response
        } else {
            val result = localTransactionRepository.getTransactionsByPeriod(id, startDate, endDate)
            TransactionState.Success(result)
        }
    }

    override val order: Int
        get() = 3

    override suspend fun createTransaction(createTransaction: CreateTransaction): CreateTransactionState {

        val dto = createTransaction.toData()

        if (!networkStatusProvider.isConnected()) {
            val localId = generateLocalId()
            localTransactionRepository.createTransaction(
                createTransaction = createTransaction,
                id = localId,
                isSynced = false,
                lastSyncedAt = null
            )
            return CreateTransactionState.OfflineSaved(localId)
        }

        val result = remoteTransactionRepository.createTransaction(dto)
        return when (result) {
            is CreateTransactionState.Success -> {
                localTransactionRepository.createTransaction(
                    createTransaction = createTransaction,
                    id = result.id,
                    isSynced = true,
                    lastSyncedAt = System.currentTimeMillis()
                )
                result
            }

            else -> result
        }

    }

    override suspend fun getDetailTransaction(id: Int): DetailTransactionState {
        return if (networkStatusProvider.isConnected()) {
            remoteTransactionRepository.getTransactionById(id)
        } else {
            runCatching {
                val transaction = localTransactionRepository.getTransactionById(id)
                DetailTransactionState.Success(transaction)
            }.getOrElse {
                DetailTransactionState.Error
            }
        }
    }

    override suspend fun updateTransaction(
        id: Int,
        updateTransaction: UpdateTransaction
    ): UpdateTransactionState {
        return if (networkStatusProvider.isConnected()) {
            val result =
                remoteTransactionRepository.updateTransaction(id, updateTransaction.toData())

            if (result is UpdateTransactionState.Success) {
                val oldEntity = localTransactionRepository.getTransactionEntityById(id)
                val article =
                    localTransactionRepository.getArticleById(updateTransaction.categoryId)

                val updated = oldEntity.copy(
                    accountId = updateTransaction.accountId,
                    articleId = updateTransaction.categoryId,
                    comment = updateTransaction.comment.sanitizeComment(),
                    amount = updateTransaction.amount.toDouble(),
                    updatedAtFromServer = Instant.now(),
                    isIncome = article.isIncome,
                    isSynced = true,
                    lastSyncedAt = System.currentTimeMillis()
                )

                localTransactionRepository.updateTransaction(updated)
            }

            result
        } else {
            val oldEntity = localTransactionRepository.getTransactionEntityById(id)
            val article = localTransactionRepository.getArticleById(updateTransaction.categoryId)

            val updated = oldEntity.copy(
                accountId = updateTransaction.accountId,
                articleId = updateTransaction.categoryId,
                comment = updateTransaction.comment.sanitizeComment(),
                amount = updateTransaction.amount.toDouble(),
                updatedAtFromServer = Instant.now(),
                isIncome = article.isIncome,
                isSynced = false,
                lastSyncedAt = null
            )

            localTransactionRepository.updateTransaction(updated)
            UpdateTransactionState.OfflineUpdated
        }
    }

    override suspend fun deleteTransaction(id: Int): DeleteTransactionState {
        return if (networkStatusProvider.isConnected()) {
            remoteTransactionRepository.deleteTransaction(id).also { result ->
                if (result is DeleteTransactionState.Success) {
                    localTransactionRepository.deleteTransaction(id)
                }
            }
        } else {
            if (id > 0) localTransactionRepository.markAsDeleted(id)
            localTransactionRepository.deleteTransaction(id)
            DeleteTransactionState.OfflineDeleted
        }
    }

    override suspend fun sync() {
        if (!networkStatusProvider.isConnected()) return


        val deletedIds = localTransactionRepository.getDeletedTransactionIds()
        for (id in deletedIds) {
            val result = remoteTransactionRepository.deleteTransaction(id)
            if (result is DeleteTransactionState.Success) {
                localTransactionRepository.removeDeletedId(id)
            }
        }

        val unsyncedLocal = localTransactionRepository.getUnsyncedTransactions()
        for (localTx in unsyncedLocal) {
            val remoteResult = runCatching { remoteTransactionRepository.getTransactionById(localTx.id) }.getOrNull()

            val resolved = if (remoteResult is DetailTransactionState.Success) {
                val remoteEntity = remoteResult.transaction.toEntity(
                    isSynced = true,
                    lastSyncedAt = System.currentTimeMillis()
                )
                conflictStrategy.resolveConflict(localTx, remoteEntity)
            } else {
                localTx
            }

            val dto = resolved.toDto()
            val syncResult = when (remoteResult) {
                is DetailTransactionState.Success -> remoteTransactionRepository.updateTransaction(resolved.id, dto)
                else -> remoteTransactionRepository.createTransaction(dto)
            }

            when (syncResult) {
                is CreateTransactionState.Success -> {
                    localTransactionRepository.deleteTransaction(localTx.id)
                    val newEntity = localTx.copy(
                        id = syncResult.id,
                        isSynced = true,
                        lastSyncedAt = System.currentTimeMillis()
                    )
                    localTransactionRepository.insertAll(listOf(newEntity))
                }

                is UpdateTransactionState.Success -> {
                    localTransactionRepository.markAsSynced(
                        resolved.copy(
                            isSynced = true,
                            lastSyncedAt = System.currentTimeMillis()
                        )
                    )
                }

                else -> Unit
            }
        }

        val serverTransactions = remoteTransactionRepository.getAllTransactions()
        if (serverTransactions is TransactionState.Success) {
            val entities = serverTransactions.items.map {
                it.toEntity(
                    isSynced = true,
                    lastSyncedAt = System.currentTimeMillis()
                )
            }

            localTransactionRepository.insertAll(entities)
        }
    }
}
