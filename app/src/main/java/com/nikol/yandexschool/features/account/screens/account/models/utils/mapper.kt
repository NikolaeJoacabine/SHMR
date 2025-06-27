package com.nikol.yandexschool.features.account.screens.account.models.utils

import com.nikol.domain.model.Account
import com.nikol.yandexschool.features.account.screens.account.models.AccountUi
import com.nikol.yandexschool.features.transaction.models.utils.formatAmountToUi

fun Account.toUi(): AccountUi {
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
