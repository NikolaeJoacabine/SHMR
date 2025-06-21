package com.nikol.domain.model


data class Account(
    val id: Int,
    val userId: Int,
    val name: String,
    val emoji: String,
    val balance: String,
    val currency: String,
    val createdAt: String,
    val updatedAt: String
)