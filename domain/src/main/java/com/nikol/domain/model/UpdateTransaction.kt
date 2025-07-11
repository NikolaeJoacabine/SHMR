package com.nikol.domain.model

import java.time.LocalDateTime

data class UpdateTransaction(
    val accountId: Int,
    val categoryId: Int,
    val amount: Int,
    val transactionDate: LocalDateTime,
    val comment: String,
)
