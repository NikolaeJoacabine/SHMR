package com.nikol.transaction.nav

import kotlinx.serialization.Serializable

@Serializable
internal data object Expenses

@Serializable
internal data object ExpensesHistory

@Serializable
internal data object ExpensesAdd

@Serializable
internal data class ExpensesEdit(val id: Int)

@Serializable
internal data object Income

@Serializable
internal data object IncomeHistory

@Serializable
internal data object IncomeAdd

@Serializable
internal data class IncomeEdit(val id: Int)
