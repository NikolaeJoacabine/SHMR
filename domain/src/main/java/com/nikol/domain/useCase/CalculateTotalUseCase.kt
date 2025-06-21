package com.nikol.domain.useCase

import com.nikol.domain.model.Transaction

class CalculateTotalUseCase {

    operator fun invoke(transactions: List<Transaction>): Int {
        return transactions.sumOf { it.amount.replace(Regex("\\s+"), "").toInt() }
    }
}