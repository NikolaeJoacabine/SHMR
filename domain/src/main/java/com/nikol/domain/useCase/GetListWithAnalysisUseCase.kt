package com.nikol.domain.useCase

import com.nikol.domain.model.Category
import com.nikol.domain.model.Transaction
import com.nikol.domain.model.TransactionType
import com.nikol.domain.repository.AccountRepository
import com.nikol.domain.repository.TransactionRepository
import com.nikol.domain.state.AccountIdState
import com.nikol.domain.state.GetCategoryState
import com.nikol.domain.state.TransactionState
import java.time.LocalDate

class GetListWithAnalysisUseCase(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(
        type: TransactionType,
        startDate: LocalDate,
        endDate: LocalDate
    ): GetCategoryState {
        return when (val responseId = accountRepository.getCurrentAccountId()) {
            is AccountIdState.Success -> {
                val result = transactionRepository.getTransactionsByPeriod(
                    id = responseId.id,
                    startDate = startDate,
                    endDate = endDate
                )

                when (result) {
                    is TransactionState.Success -> {
                        val listTransaction = result.items
                            .filter { it.isIncome == (type == TransactionType.Income) }

                        val listCategory = getCategories(listTransaction)
                        GetCategoryState.Success(listCategory)
                    }

                    is TransactionState.NetworkError -> GetCategoryState.Error
                    is TransactionState.Error -> GetCategoryState.Error
                }
            }

            is AccountIdState.NoInternet -> GetCategoryState.Error
            is AccountIdState.Error -> GetCategoryState.Error
        }
    }

    private fun getCategories(transactions: List<Transaction>): List<Category> {
        val totalAmount = transactions.sumOf { it.amount }

        return transactions
            .groupBy { it.articleId }
            .map { (articleId, grouped) ->
                val amount = grouped.sumOf { it.amount }
                val first = grouped.first()

                val percent = if (totalAmount == 0) 0.0 else (amount.toDouble() * 100 / totalAmount)

                Category(
                    id = articleId,
                    name = first.category,
                    amount = amount,
                    emoji = first.emoji,
                    percent = percent
                )
            }
    }
}
