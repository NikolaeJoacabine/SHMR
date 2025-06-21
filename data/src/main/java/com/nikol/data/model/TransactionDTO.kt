package com.nikol.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransactionDTO(
    @SerialName("account")
    val accountDTO: AccountDTO? = null,
    @SerialName("amount")
    val amount: String? = null,
    @SerialName("category")
    val categoryDTO: CategoryDTO? = null,
    @SerialName("comment")
    val comment: String? = null,
    @SerialName("createdAt")
    val createdAt: String? = null,
    @SerialName("id")
    val id: Int? = null,
    @SerialName("transactionDate")
    val transactionDate: String? = null,
    @SerialName("updatedAt")
    val updatedAt: String? = null
)