package com.nikol.yandexschool.domain.model

data class Amount(
    val id: Int,
    val name: String,
    val emoji: String,
    val isIncome: Boolean
)