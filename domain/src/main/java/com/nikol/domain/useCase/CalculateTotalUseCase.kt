package com.nikol.domain.useCase

import com.nikol.domain.model.Transaction

/**
 * Use case для вычисления общей суммы транзакций.
 *
 * При вызове со списком транзакций возвращает сумму всех их значений `amount`.
 */
class CalculateTotalUseCase {

    operator fun invoke(transactions: List<Transaction>): Int {
        return transactions.sumOf { it.amount }
    }
}
