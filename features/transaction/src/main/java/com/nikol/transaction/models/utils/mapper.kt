package com.nikol.transaction.models.utils

import com.nikol.domain.model.Transaction
import com.nikol.domain.model.TransactionDetail
import com.nikol.transaction.models.TransactionAddUi
import com.nikol.transaction.models.TransactionHistoryUi
import com.nikol.transaction.models.TransactionUi
import com.nikol.ui.formatter.toDateTimeString
import com.nikol.ui.utils.formatAmountToUi
import com.nikol.ui.utils.formatCreatedAt
import com.nikol.ui.utils.formatCreatedAtHistory
import java.time.Instant
import java.time.ZoneId


fun Transaction.toUi(): TransactionUi {
    return TransactionUi(
        id = id,
        category = category,
        comment = comment,
        emoji = emoji,
        amount = formatAmountToUi(amount),
        createdAt = formatCreatedAt(createdAt),
        isIncome = isIncome
    )
}

fun Transaction.toHistoryUi(): TransactionHistoryUi {
    return TransactionHistoryUi(
        id = id,
        category = category,
        comment = comment,
        emoji = emoji,
        amount = formatAmountToUi(amount),
        createdAt = formatCreatedAtHistory(createdAt),
        isIncome = isIncome
    )
}

fun TransactionDetail.toUi(): TransactionAddUi {
    return TransactionAddUi(
        accountName = accountType.name,
        accountId = accountType.id,
        articlesName = incomeType.name,
        articlesId = incomeType.id,
        amount = amount.toInt(),
        dateTime = Instant.parse(updatedAt)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime(),
        comment = comment,
        lastSynchronized = lastSynchronized?.toDateTimeString()
    )
}
