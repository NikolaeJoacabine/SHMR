package com.nikol.account.models

/**
 * UI-модель аккаунта, используемая для отображения данных в пользовательском интерфейсе.
 *
 * @property id Уникальный идентификатор аккаунта.
 * @property userId Идентификатор пользователя, которому принадлежит аккаунт.
 * @property name Название аккаунта.
 * @property emoji Эмодзи, ассоциированное с аккаунтом.
 * @property balance Баланс аккаунта в формате строки, подготовленной для отображения.
 * @property currency Валюта аккаунта.
 * @property createdAt Дата создания аккаунта в формате строки.
 * @property updatedAt Дата последнего обновления аккаунта в формате строки.
 */
internal data class AccountUi(
    val id: Int,
    val userId: Int,
    val name: String,
    val emoji: String,
    val balance: String,
    val currency: String,
    val createdAt: String,
    val updatedAt: String
)
