package com.nikol.domain.useCase

import com.nikol.domain.model.TransactionType
import com.nikol.domain.repository.AccountRepository
import com.nikol.domain.repository.TransactionRepository
import com.nikol.domain.state.AccountIdState
import com.nikol.domain.state.TransactionState

/**
 * Use case для получения транзакций за сегодняшний день с фильтрацией по типу.
 *
 * Получает текущий идентификатор аккаунта через [AccountRepository],
 * затем загружает транзакции за сегодняшний день из [TransactionRepository],
 * фильтрует транзакции по типу (доходы или расходы) и сортирует их по дате создания в порядке убывания.
 *
 * @param type тип транзакций для фильтрации — доходы или расходы.
 *
 * @return [TransactionState] — состояние результата получения и фильтрации транзакций.
 */
class GetTransactionsForTodayUseCase(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository
) {

    suspend operator fun invoke(type: TransactionType): TransactionState {
        return when (val responseId = accountRepository.getCurrentAccountId()) {
            is AccountIdState.Success -> {
                val result = transactionRepository.getTransactionsForToday(responseId.id)

                when (result) {
                    is TransactionState.Success -> {
                        val list = result.items
                            .filter { it.isIncome == (type == TransactionType.Income) }
                            .sortedByDescending { it.createdAt }
                        TransactionState.Success(list)
                    }

                    is TransactionState.Error -> TransactionState.Error("")
                    is TransactionState.NetworkError -> TransactionState.NetworkError
                }
            }

            is AccountIdState.Error -> TransactionState.Error("")
            is AccountIdState.NoInternet -> TransactionState.NetworkError
        }
    }
}
