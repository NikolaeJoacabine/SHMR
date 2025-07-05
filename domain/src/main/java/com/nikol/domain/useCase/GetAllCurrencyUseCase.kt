package com.nikol.domain.useCase

import com.nikol.domain.model.CurrencyType

class GetAllCurrencyUseCase {

    operator fun invoke(): List<CurrencyType> {
        return CurrencyType.entries
    }
}
