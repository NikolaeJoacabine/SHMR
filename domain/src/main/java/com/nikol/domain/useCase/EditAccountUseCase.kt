package com.nikol.domain.useCase

import com.nikol.domain.model.AccountUpdateRequest
import com.nikol.domain.repository.AccountRepository
import com.nikol.domain.state.AccountEditState

class EditAccountUseCase(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(
        accountUpdateRequest: AccountUpdateRequest,
        id: Int
    ): AccountEditState {
        return accountRepository.editAccount(accountUpdateRequest, id)
    }
}
