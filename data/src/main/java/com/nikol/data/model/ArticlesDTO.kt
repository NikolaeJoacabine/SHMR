package com.nikol.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO класс для передачи данных статьи (Article), сериализуемый с помощью Kotlinx Serialization.
 *
 * Все поля являются nullable, так как они могут отсутствовать в ответе API.
 *
 * @property emoji Эмодзи, связанное со статьей.
 * @property id Уникальный идентификатор статьи.
 * @property isIncome Флаг, указывающий, является ли статья доходной.
 * @property name Название статьи.
 */
@Serializable
data class ArticlesDTO(
    @SerialName("emoji")
    val emoji: String? = null,
    @SerialName("id")
    val id: Int,
    @SerialName("isIncome")
    val isIncome: Boolean,
    @SerialName("name")
    val name: String
)
