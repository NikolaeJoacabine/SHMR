package com.nikol.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateTransactionDTO(
    @SerialName("accountId")
    val accountId: Int,
    @SerialName("amount")
    val amount: String,
    @SerialName("categoryId")
    val categoryId: Int,
    @SerialName("comment")
    val comment: String?,
    @SerialName("transactionDate")
    val transactionDate: String
)
