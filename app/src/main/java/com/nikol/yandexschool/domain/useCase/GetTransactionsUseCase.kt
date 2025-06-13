package com.nikol.yandexschool.domain.useCase

import com.nikol.yandexschool.data.FakeData
import com.nikol.yandexschool.domain.model.Transaction
import com.nikol.yandexschool.domain.model.TransactionType

class GetTransactionsUseCase {

    //пока берём замоканные данные
    operator fun invoke(type: TransactionType): List<Transaction> {
        return when (type) {
            TransactionType.Expenses -> FakeData.listExpenses
            TransactionType.Income -> FakeData.listIncome
        }
    }
}