package com.nikol.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AccountEditDTO(
    val id: Int,
    val userId: Int,
    val name: String,
    val balance: String,
    val currency: String,
    val createdAt: String,
    val updatedAt: String
)
