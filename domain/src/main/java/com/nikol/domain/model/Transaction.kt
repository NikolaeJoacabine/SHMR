package com.nikol.domain.model

data class Transaction(
    val id: Int,
    val category: String,
    val comment: String? = null,
    val emoji: String? = null,
    val amount: String,
)

data class TransactionDetail(
    val id: Int,
    val incomeType: Amount,
    val accountType: Account,
    val comment: String,
    val amount: String,
    val createdAt: String,
    val updatedAt: String,
)