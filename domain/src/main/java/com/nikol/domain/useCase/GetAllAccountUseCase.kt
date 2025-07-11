package com.nikol.domain.useCase

import com.nikol.domain.repository.AccountRepository
import com.nikol.domain.state.AccountState

class GetAllAccountUseCase(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(): AccountState {
        return accountRepository.getAccount()
    }
}
