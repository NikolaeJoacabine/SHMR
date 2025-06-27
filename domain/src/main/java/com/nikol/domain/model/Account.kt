package com.nikol.domain.model


/**
 * Доменная модель аккаунта пользователя.
 *
 * @property id Уникальный идентификатор аккаунта.
 * @property userId Идентификатор пользователя, которому принадлежит аккаунт.
 * @property name Название аккаунта.
 * @property emoji Эмодзи, ассоциированное с аккаунтом.
 * @property balance Баланс аккаунта в целочисленном формате.
 * @property currency Валюта баланса.
 * @property createdAt Дата и время создания аккаунта в виде строки.
 * @property updatedAt Дата и время последнего обновления аккаунта в виде строки.
 */
data class Account(
    val id: Int,
    val userId: Int,
    val name: String,
    val emoji: String,
    val balance: Int,
    val currency: String,
    val createdAt: String,
    val updatedAt: String
)
