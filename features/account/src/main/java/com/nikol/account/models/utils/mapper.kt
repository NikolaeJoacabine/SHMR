package com.nikol.account.models.utils

import com.nikol.account.models.AccountUi
import com.nikol.domain.model.Account
import com.nikol.ui.utils.formatAmountToUi

internal fun Account.toUi(): AccountUi {
    return AccountUi(
        id = id,
        userId = userId,
        name = name,
        emoji = emoji,
        balance = formatAmountToUi(balance),
        currency = currency,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
