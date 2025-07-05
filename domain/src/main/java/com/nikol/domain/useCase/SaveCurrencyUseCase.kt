package com.nikol.domain.useCase

import com.nikol.domain.model.CurrencyType
import com.nikol.domain.repository.AccountRepository

class SaveCurrencyUseCase(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(currencyType: CurrencyType) {
        accountRepository.saveCurrency(currencyType)
    }
}
