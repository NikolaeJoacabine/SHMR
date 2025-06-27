package com.nikol.domain.useCase

import com.nikol.domain.model.TransactionType
import com.nikol.domain.repository.AccountRepository
import com.nikol.domain.repository.TransactionRepository
import com.nikol.domain.state.AccountIdState
import com.nikol.domain.state.TransactionState

import java.time.LocalDate

/**
 * Use case для получения транзакций за указанный период с фильтрацией по типу.
 *
 * Получает текущий идентификатор аккаунта через [AccountRepository],
 * затем загружает транзакции за период из [TransactionRepository],
 * фильтрует транзакции по типу (доходы или расходы) и сортирует их по дате создания в порядке убывания.
 *
 * @param type тип транзакций для фильтрации — доходы или расходы.
 * @param startDate начальная дата периода.
 * @param endDate конечная дата периода.
 *
 * @return [TransactionState] — состояние результата получения и фильтрации транзакций.
 */
class GetTransactionsByPeriodUseCase(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository
) {

    suspend operator fun invoke(
        type: TransactionType,
        startDate: LocalDate,
        endDate: LocalDate
    ): TransactionState {
        return when (val responseId = accountRepository.getCurrentAccountId()) {
            is AccountIdState.Success -> {
                val result = transactionRepository.getTransactionsByPeriod(
                    id = responseId.id,
                    startDate = startDate,
                    endDate = endDate
                )

                when (result) {
                    is TransactionState.Success -> {
                        val filteredList = result.items
                            .filter { it.isIncome == (type == TransactionType.Income) }
                            .sortedByDescending { it.createdAt }
                        TransactionState.Success(filteredList)
                    }

                    is TransactionState.NetworkError -> TransactionState.NetworkError
                    is TransactionState.Error -> TransactionState.Error("")
                }
            }

            is AccountIdState.NoInternet -> TransactionState.NetworkError
            is AccountIdState.Error -> TransactionState.Error("")
        }
    }
}
