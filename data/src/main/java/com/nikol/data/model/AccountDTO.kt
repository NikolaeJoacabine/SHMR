package com.nikol.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO класс для передачи данных аккаунта, сериализуемый с помощью Kotlinx Serialization.
 *
 * Все поля являются nullable, так как они могут отсутствовать в ответе API.
 *
 * @property balance Баланс аккаунта в строковом формате.
 * @property createdAt Дата создания аккаунта в формате строки.
 * @property currency Валюта аккаунта.
 * @property id Уникальный идентификатор аккаунта.
 * @property name Имя аккаунта.
 * @property updatedAt Дата последнего обновления аккаунта.
 * @property userId Идентификатор пользователя, которому принадлежит аккаунт.
 */
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
