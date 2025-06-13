package com.nikol.yandexschool.domain.useCase

import com.nikol.yandexschool.data.FakeData
import com.nikol.yandexschool.domain.model.Amount

class GetAmountUseCase {

    operator fun invoke(): List<Amount> {
        return FakeData.listAmount
    }
}