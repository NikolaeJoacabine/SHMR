package com.nikol.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateTransactionResultDTO(
    @SerialName("accountId")
    val accountId: Int,
    @SerialName("amount")
    val amount: String,
    @SerialName("categoryId")
    val categoryId: Int,
    @SerialName("comment")
    val comment: String?,
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("id")
    val id: Int,
    @SerialName("transactionDate")
    val transactionDate: String,
    @SerialName("updatedAt")
    val updatedAt: String
)
