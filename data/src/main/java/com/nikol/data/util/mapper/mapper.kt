package com.nikol.data.util.mapper

import com.nikol.data.model.AccountDTO
import com.nikol.data.model.ArticlesDTO
import com.nikol.data.model.TransactionDTO
import com.nikol.data.util.formater.formatAmount
import com.nikol.data.util.formater.formatCreatedAt
import com.nikol.domain.model.Account
import com.nikol.domain.model.Articles
import com.nikol.domain.model.Transaction
import java.text.NumberFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun TransactionDTO.toDomain(): Transaction {
    return Transaction(
        id = id ?: 1,
        category = categoryDTO?.name ?: "",
        comment = comment ?: "",
        emoji = categoryDTO?.emoji ?: "",
        amount = formatAmount(amount),
        isIncome = categoryDTO?.isIncome == true,
        createdAt = formatCreatedAt(createdAt)
    )
}


fun ArticlesDTO.toDomain(): Articles {
    return Articles(
        id = id ?: 1,
        name = name ?: "",
        emoji = emoji ?: "",
        isIncome = isIncome == true
    )
}

fun AccountDTO.toDomain(): Account {
    return Account(
        id = id ?: 1,
        userId = userId ?: 1,
        name = name ?: "",
        emoji = "\uD83D\uDCB0",
        balance = formatAmount(balance),
        currency = currency ?: "",
        createdAt = createdAt ?: "",
        updatedAt = updatedAt ?: ""
    )
}