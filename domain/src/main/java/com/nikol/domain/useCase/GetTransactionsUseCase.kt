package com.nikol.domain.useCase

import com.nikol.domain.FakeData
import com.nikol.domain.model.Transaction
import com.nikol.domain.model.TransactionType

class GetTransactionsUseCase {

    //пока берём замоканные данные
    operator fun invoke(type: TransactionType): List<Transaction> {
        return when (type) {
            TransactionType.Expenses -> FakeData.listExpenses
            TransactionType.Income -> FakeData.listIncome
        }
    }
}