package com.nikol.domain.useCase

import com.nikol.domain.FakeData
import com.nikol.domain.model.Amount

class GetArticlesUseCase {

    operator fun invoke(): List<Amount> {
        return FakeData.listAmount
    }
}