package com.nikol.transaction.models.utils

import com.nikol.domain.model.Transaction
import com.nikol.transaction.models.TransactionHistoryUi
import com.nikol.transaction.models.TransactionUi
import com.nikol.ui.utils.formatAmountToUi
import com.nikol.ui.utils.formatCreatedAt
import com.nikol.ui.utils.formatCreatedAtHistory


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
