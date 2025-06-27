package com.nikol.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO класс для передачи данных категории (Category), сериализуемый с помощью Kotlinx Serialization.
 *
 * Все поля nullable, так как API может не возвращать некоторые значения.
 *
 * @property emoji Эмодзи, связанное с категорией.
 * @property id Уникальный идентификатор категории.
 * @property isIncome Флаг, указывающий, является ли категория доходной.
 * @property name Название категории.
 */
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
