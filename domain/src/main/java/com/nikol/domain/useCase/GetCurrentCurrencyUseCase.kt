package com.nikol.domain.useCase

import com.nikol.domain.model.CurrencyType
import com.nikol.domain.repository.AccountRepository

class GetCurrentCurrencyUseCase(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(): CurrencyType {
        return accountRepository.getCurrentCurrency()
    }
}
