package com.nikol.data.transaction.local

import com.nikol.data.account.local.database.AccountDao
import com.nikol.data.articles.local.database.ArticleEntity
import com.nikol.data.articles.local.database.ArticlesDao
import com.nikol.data.transaction.local.database.TransactionDao
import com.nikol.data.transaction.local.database.TransactionEntity
import com.nikol.data.transaction.local.database.delete.DeletedTransactionDao
import com.nikol.data.transaction.local.database.delete.DeletedTransactionEntity
import com.nikol.data.util.mapper.toDomain
import com.nikol.data.util.mapper.toEntity
import com.nikol.domain.model.CreateTransaction
import com.nikol.domain.model.Transaction
import com.nikol.domain.model.TransactionDetail
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import javax.inject.Inject

class LocalTransactionRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao,
    private val articleDao: ArticlesDao,
    private val accountDao: AccountDao,
    private val deletedTransactionDao: DeletedTransactionDao
) : LocalTransactionRepository {

    override suspend fun transactionForToday(id: Int): List<Transaction> {
        val today = Instant.now()
            .atZone(ZoneOffset.UTC)
            .toLocalDate()
            .toString()
        return transactionDao.transactionForToday(id, today).map {
            val article = articleDao.getArticleById(it.articleId)
            it.toDomain(categoryName = article.name, emoji = article.emoji)
        }
    }

    override suspend fun getTransactionsByPeriod(
        id: Int,
        startDate: LocalDate,
        endDate: LocalDate
    ): List<Transaction> {
        return transactionDao.getTransactionsByPeriod(
            id = id,
            startDate = startDate.toString(),
            endDate = endDate.toString()
        ).map {
            val article = articleDao.getArticleById(it.articleId)
            it.toDomain(categoryName = article.name, emoji = article.emoji)
        }
    }

    override suspend fun createTransaction(transactionEntity: TransactionEntity) {
        transactionDao.createTransaction(transactionEntity)
    }

    override suspend fun getTransactionById(id: Int): TransactionDetail {
        val transaction = transactionDao.getTransactionById(id)
        val article = articleDao.getArticleById(transaction.articleId)
        val account = accountDao.getAccountById(transaction.accountId)
        return transaction.toDomain(article, account!!)
    }

    override suspend fun getArticleById(id: Int): ArticleEntity {
        return articleDao.getArticleById(id)
    }

    override suspend fun updateTransaction(transactionEntity: TransactionEntity) {
        transactionDao.updateTransaction(transactionEntity)
    }

    override suspend fun deleteTransaction(id: Int) {
        transactionDao.deleteTransaction(id)
    }

    override suspend fun insertAll(transactions: List<TransactionEntity>) {
        transactionDao.insertAll(transactions)
    }

    override suspend fun createTransaction(
        createTransaction: CreateTransaction,
        id: Int,
        isSynced: Boolean,
        lastSyncedAt: Long?
    ) {
        val article = articleDao.getArticleById(createTransaction.categoryId)
        val entity = createTransaction.toEntity(
            id = id,
            article = article,
            isSynced = isSynced,
            lastSyncedAt = lastSyncedAt
        )
        transactionDao.createTransaction(entity)
    }

    override suspend fun getTransactionEntityById(id: Int): TransactionEntity {
        return transactionDao.getTransactionById(id)
    }

    override suspend fun getUnsyncedTransactions(): List<TransactionEntity> {
        return transactionDao.getUnsynced()
    }

    override suspend fun getDeletedTransactionIds(): List<Int> {
        return deletedTransactionDao.getAll().map { it.id }
    }

    override suspend fun removeDeletedId(id: Int) {
        deletedTransactionDao.remove(id)
    }

    override suspend fun markAsSynced(entity: TransactionEntity) {
        transactionDao.updateTransaction(
            entity.copy(
                isSynced = true,
                lastSyncedAt = System.currentTimeMillis()
            )
        )
    }

    override suspend fun markAsDeleted(id: Int) {
        deletedTransactionDao.insert(DeletedTransactionEntity(id))
    }

    override suspend fun hasUnsynced(): Boolean {
        return getUnsyncedTransactions().isNotEmpty() || getDeletedTransactionIds().isNotEmpty()
    }
}
