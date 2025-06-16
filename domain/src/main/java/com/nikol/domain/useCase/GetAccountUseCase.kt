package com.nikol.domain.useCase

import com.nikol.domain.FakeData
import com.nikol.domain.model.Account

class GetAccountUseCase {
    operator fun invoke(): List<Account> {
        return FakeData.listAccount
    }
}