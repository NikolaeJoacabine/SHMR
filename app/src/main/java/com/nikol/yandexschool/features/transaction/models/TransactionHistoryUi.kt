package com.nikol.yandexschool.features.transaction.models

data class TransactionHistoryUi(
    val id: Int,
    val category: String,
    val comment: String? = null,
    val emoji: String? = null,
    val amount: String,
    val createdAt: String,
    val isIncome: Boolean
)
