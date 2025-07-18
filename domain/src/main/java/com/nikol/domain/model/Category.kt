package com.nikol.domain.model

data class Category(
    val id: Int,
    val name: String,
    val amount: Int,
    val emoji: String?,
    val percent: Double,
)
