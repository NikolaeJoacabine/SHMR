package com.nikol.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountDTO(
    @SerialName("balance")
    val balance: String? = null,
    @SerialName("createdAt")
    val createdAt: String? = null,
    @SerialName("currency")
    val currency: String? = null,
    @SerialName("id")
    val id: Int? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("updatedAt")
    val updatedAt: String? = null,
    @SerialName("userId")
    val userId: Int? = null
)