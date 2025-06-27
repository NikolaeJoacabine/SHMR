package com.nikol.domain.useCase

import com.nikol.domain.repository.AccountRepository
import com.nikol.domain.state.AccountState

/**
 * Use case для получения информации об аккаунте.
 *
 * Делегирует вызов репозиторию аккаунтов и возвращает состояние аккаунта
 * (успех с данными, ошибка или отсутствие интернета).
 *
 * @return [AccountState] — текущее состояние получения аккаунта.
 */
class GetAccountUseCase(private val accountRepository: AccountRepository) {
    suspend operator fun invoke(): AccountState {
        return accountRepository.getAccount()
    }
}

