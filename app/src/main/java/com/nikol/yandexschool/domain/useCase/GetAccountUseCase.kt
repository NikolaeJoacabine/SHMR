package com.nikol.yandexschool.domain.useCase

import com.nikol.yandexschool.data.FakeData
import com.nikol.yandexschool.domain.model.Account

class GetAccountUseCase {
    operator fun invoke(): List<Account> {
        return FakeData.listAccount
    }
}