package com.nikol.domain.model

import java.time.Instant

/**
 * Доменная модель транзакции.
 *
 * @property id Уникальный идентификатор транзакции.
 * @property category Название категории транзакции.
 * @property comment Комментарий к транзакции (необязательно).
 * @property emoji Эмодзи, связанное с категорией транзакции (необязательно).
 * @property amount Сумма транзакции в целочисленном формате.
 * @property createdAt Время создания транзакции в формате Instant.
 * @property isIncome Флаг, указывающий, является ли транзакция доходом (true) или расходом (false).
 */
data class Transaction(
    val id: Int,
    val category: String,
    val comment: String? = null,
    val emoji: String? = null,
    val amount: Int,
    val createdAt: Instant,
    val isIncome: Boolean
)

/**
 * Детализированная доменная модель транзакции.
 *
 * @property id Уникальный идентификатор транзакции.
 * @property incomeType Тип статьи/дохода (объект Articles).
 * @property accountType Тип счёта, с которым связана транзакция (объект Account).
 * @property comment Комментарий к транзакции.
 * @property amount Сумма транзакции в виде строки.
 * @property createdAt Дата и время создания транзакции в виде строки.
 * @property updatedAt Дата и время последнего обновления транзакции в виде строки.
 */
data class TransactionDetail(
    val id: Int,
    val incomeType: Articles,
    val accountType: Account,
    val comment: String,
    val amount: String,
    val createdAt: String,
    val updatedAt: String,
)
