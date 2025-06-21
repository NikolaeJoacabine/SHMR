package com.nikol.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDTO(
    @SerialName("emoji")
    val emoji: String? = null,
    @SerialName("id")
    val id: Int? = null,
    @SerialName("isIncome")
    val isIncome: Boolean? = null,
    @SerialName("name")
    val name: String? = null
)