package com.nikol.transaction.models

import java.time.LocalDateTime

data class TransactionAddUi(
    val accountName: String? = null,
    val accountId: Int? = null,
    val articlesName: String? = null,
    val articlesId: Int? = null,
    val amount: Int? = null,
    val dateTime: LocalDateTime = LocalDateTime.now(),
    val comment: String? = null
)
