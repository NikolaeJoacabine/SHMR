package com.nikol.domain.useCase

import com.nikol.domain.repository.AccountRepository
import com.nikol.domain.state.AccountByIdState

class GetAccountByIdUseCase(
    private val accountRepository: AccountRepository
) {

    suspend operator fun invoke(id: Int): AccountByIdState {
        return accountRepository.getAccountById(id)
    }
}
