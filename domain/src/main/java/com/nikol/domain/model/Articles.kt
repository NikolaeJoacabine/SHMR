package com.nikol.domain.model

/**
 * Доменная модель статьи (категории или типа дохода/расхода).
 *
 * @property id Уникальный идентификатор статьи.
 * @property name Название статьи.
 * @property emoji Эмодзи, ассоциированное с данной статьёй.
 * @property isIncome Флаг, указывающий, относится ли статья к доходам (true) или расходам (false).
 */
data class Articles(
    val id: Int,
    val name: String,
    val emoji: String,
    val isIncome: Boolean
)

