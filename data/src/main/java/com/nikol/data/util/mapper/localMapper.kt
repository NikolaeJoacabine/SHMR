package com.nikol.data.util.mapper

import com.nikol.data.account.local.database.AccountEntity
import com.nikol.data.articles.local.database.ArticleEntity
import com.nikol.data.model.ArticlesDTO
import com.nikol.data.model.CreateTransactionDTO
import com.nikol.data.transaction.local.database.TransactionEntity
import com.nikol.data.util.sanitizeComment
import com.nikol.domain.model.Account
import com.nikol.domain.model.AccountType
import com.nikol.domain.model.Articles
import com.nikol.domain.model.CategoryType
import com.nikol.domain.model.CreateTransaction
import com.nikol.domain.model.Transaction
import com.nikol.domain.model.TransactionDetail
import com.nikol.domain.model.UpdateTransaction
import java.time.Instant
import java.time.ZoneOffset

fun ArticleEntity.toDomain(): Articles {
    return Articles(
        id = id,
        name = name,
        emoji = emoji,
        isIncome = isIncome
    )
}

fun Articles.toEntity(
    isSynced: Boolean,
    lastSyncedAt: Long
): ArticleEntity {
    return ArticleEntity(
        id = id,
        name = name,
        emoji = emoji,
        isIncome = isIncome,
        isSynced = isSynced,
        lastSyncedAt = lastSyncedAt
    )
}
fun ArticleEntity.toDto(): ArticlesDTO {
    return ArticlesDTO(
        id = id,
        name = name,
        emoji = emoji,
        isIncome = isIncome
    )
}
fun ArticlesDTO.toEntity(
    isSynced: Boolean,
    lastSyncedAt: Long
): ArticleEntity {
    return ArticleEntity(
        id = id,
        emoji = emoji ?: "",
        isIncome = isIncome,
        name = name,
        isSynced = isSynced,
        lastSyncedAt = lastSyncedAt
    )
}

fun AccountEntity.toDomain(): Account {
    return Account(
        id = accountId,
        balance = balance.toInt(),
        createdAt = createdAt,
        currency = currency,
        name = name,
        updatedAt = updatedAt,
        userId = userId,
        emoji = emoji,
    )
}

fun Account.toEntity(isSynced: Boolean, lastSyncedAt: Long? = null): AccountEntity {
    return AccountEntity(
        accountId = id,
        balance = balance.toString(),
        emoji = emoji,
        createdAt = createdAt,
        currency = currency,
        name = name,
        updatedAt = updatedAt,
        userId = userId,
        isSynced = isSynced,
        lastSyncedAt = lastSyncedAt,
    )
}

fun TransactionEntity.toDomain(
    article: ArticleEntity,
    account: AccountEntity
): TransactionDetail {
    return TransactionDetail(
        id = id,
        incomeType = CategoryType(
            id = article.id,
            name = article.name,
            isIncome = article.isIncome
        ),
        accountType = AccountType(id = account.accountId, name = account.name),
        comment = comment.sanitizeComment(),
        amount = amount.toInt().toString(),
        createdAt = createdAtFromServer.toString(),
        updatedAt = updatedAtFromServer.toString(),
        lastSynchronized = lastSyncedAt
    )
}

fun TransactionEntity.toDomain(
    categoryName: String,
    emoji: String
): Transaction {
    return Transaction(
        id = id,
        category = categoryName,
        comment = comment.sanitizeComment(),
        emoji = emoji,
        amount = amount.toInt(),
        createdAt = createdAtFromServer,
        updatedAt = updatedAtFromServer,
        isIncome = isIncome,
        accountId = accountId,
        articleId = articleId
    )
}

fun Transaction.toEntity(
    isSynced: Boolean,
    lastSyncedAt: Long? = null
): TransactionEntity {
    return TransactionEntity(
        id = id,
        accountId = accountId,
        articleId = articleId,
        comment = comment.sanitizeComment(),
        amount = amount.toDouble(),
        createdAtFromServer = createdAt,
        updatedAtFromServer = updatedAt,
        isIncome = isIncome,
        isSynced = isSynced,
        lastSyncedAt = lastSyncedAt
    )
}

fun CreateTransaction.toEntity(
    id: Int,
    article: ArticleEntity,
    isSynced: Boolean,
    lastSyncedAt: Long?
): TransactionEntity {
    return TransactionEntity(
        id = id,
        accountId = accountId,
        articleId = categoryId,
        comment = comment.sanitizeComment(),
        amount = amount.toDouble(),
        createdAtFromServer = transactionDate.toInstant(ZoneOffset.UTC),
        updatedAtFromServer = transactionDate.toInstant(ZoneOffset.UTC),
        isIncome = article.isIncome,
        isSynced = isSynced,
        lastSyncedAt = lastSyncedAt
    )
}

fun TransactionEntity.toDto(): CreateTransactionDTO {
    return CreateTransactionDTO(
        accountId = accountId,
        amount = amount.toString(),
        categoryId = articleId,
        comment = comment.sanitizeComment(),
        transactionDate = createdAtFromServer.toString()
    )
}

fun TransactionDetail.toEntity(
    isSynced: Boolean,
    lastSyncedAt: Long? = null,
): TransactionEntity {
    return TransactionEntity(
        id = id,
        accountId = accountType.id,
        articleId = incomeType.id,
        comment = comment.sanitizeComment(),
        amount = amount.toDouble(),
        createdAtFromServer = Instant.parse(createdAt),
        updatedAtFromServer = Instant.parse(updatedAt),
        isIncome = incomeType.isIncome,
        isSynced = isSynced,
        lastSyncedAt = lastSyncedAt
    )
}

