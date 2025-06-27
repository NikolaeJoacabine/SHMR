package com.nikol.yandexschool.features.transaction.models.utils

import com.nikol.domain.model.Transaction
import com.nikol.yandexschool.features.transaction.models.TransactionHistoryUi
import com.nikol.yandexschool.features.transaction.models.TransactionUi


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
