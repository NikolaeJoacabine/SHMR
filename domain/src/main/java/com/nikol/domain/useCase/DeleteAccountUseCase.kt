package com.nikol.domain.useCase

import com.nikol.domain.repository.AccountRepository
import com.nikol.domain.state.AccountDeleteState

class DeleteAccountUseCase(
    private val accountRepository: AccountRepository
) {

    suspend operator fun invoke(id: Int): AccountDeleteState {
        return accountRepository.deleteAccount(id)
    }
}
