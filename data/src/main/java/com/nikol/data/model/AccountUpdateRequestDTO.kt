package com.nikol.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AccountUpdateRequestDTO(
    val name: String,
    val balance: String,
    val currency: String
)
