package com.nikol.domain.model

import java.time.LocalDateTime

data class CreateTransaction(
    val accountId: Int,
    val categoryId: Int,
    val amount: Int,
    val transactionDate: LocalDateTime,
    val comment: String?
)
