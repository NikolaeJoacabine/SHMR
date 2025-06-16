package com.nikol.domain

import com.nikol.domain.model.Account
import com.nikol.domain.model.Amount
import com.nikol.domain.model.Transaction

object FakeData {
    val listExpenses = listOf(
        Transaction(
            id = 0,
            category = "Аренда квартиры",
            emoji = "🏡",
            amount = "100 000",
        ),
        Transaction(
            id = 1,
            category = "Одежда",
            emoji = "👗",
            amount = "100 000",
        ),
        Transaction(
            id = 2,
            category = "На собачку",
            comment = "Джек",
            emoji = "🐶",
            amount = "100 000",
        ),
        Transaction(
            id = 3,
            category = "На собачку",
            comment = "Энни",
            emoji = "🐶",
            amount = "100 000",
        ),
        Transaction(
            id = 4,
            category = "Ремонт квартиры",
            emoji = "РК",
            amount = "100 000",
        ),
        Transaction(
            id = 5,
            category = "Продукты",
            emoji = "🍭",
            amount = "100 000",
        ),
        Transaction(
            id = 6,
            category = "Спортзал",
            emoji = "🏋️",
            amount = "100 000",
        ),
        Transaction(
            id = 7,
            category = "Медицина",
            emoji = "💊",
            amount = "100 000",
        )
    )
    val listIncome = listOf(
        Transaction(
            id = 0,
            category = "Зарплата",
            amount = "500 000",

            ),
        Transaction(
            id = 1,
            category = "подработка",
            amount = "500 000"
        ),
    )

    val listAccount = listOf(
        Account(
            id = 0,
            name = "Баланс",
            userId = 0,
            balance = "675 000",
            currency = "RUB",
            createdAt = "2025-06-13T13:30:23.058Z",
            updatedAt = "2025-06-13T13:30:23.058Z",
            emoji = "💰"
        )
    )

    val listAmount = listOf(
        Amount(
            id = 0,
            name = "Аренда квартиры",
            emoji = "🏡",
            isIncome = false
        ),
        Amount(
            id = 1,
            name = "Одежда",
            emoji = "👗",
            isIncome = false
        ),
        Amount(
            id = 2,
            name = "На собачку",
            emoji = "🐶",
            isIncome = false
        ),
        Amount(
            id = 3,
            name = "На собачку",
            emoji = "🐶",
            isIncome = false
        ),
        Amount(
            id = 4,
            name = "Ремонт квартиры",
            emoji = "РК",
            isIncome = false
        ),
        Amount(
            id = 5,
            name = "Продукты",
            emoji = "🍭",
            isIncome = false
        ),
        Amount(
            id = 6,
            name = "Спортзал",
            emoji = "🏋️",
            isIncome = false
        ),
        Amount(
            id = 7,
            name = "Медицина",
            emoji = "💊",
            isIncome = false
        )
    )
}