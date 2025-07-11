package com.nikol.transaction.screens.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nikol.domain.model.Account
import com.nikol.domain.model.Articles
import com.nikol.domain.model.TransactionType
import com.nikol.domain.model.UpdateTransaction
import com.nikol.domain.state.AccountState
import com.nikol.domain.state.ArticlesState
import com.nikol.domain.state.DeleteTransactionState
import com.nikol.domain.state.DetailTransactionState
import com.nikol.domain.state.UpdateTransactionState
import com.nikol.domain.useCase.DeleteTransactionUseCase
import com.nikol.domain.useCase.GetAllAccountUseCase
import com.nikol.domain.useCase.GetFilteredArticlesUseCase
import com.nikol.domain.useCase.GetTransactionByIdUseCase
import com.nikol.domain.useCase.UpdateTransactionUseCase
import com.nikol.transaction.models.TransactionAddUi
import com.nikol.transaction.models.utils.toUi
import com.nikol.transaction.screens.edit.stateHoisting.AccountsStateEdit
import com.nikol.transaction.screens.edit.stateHoisting.ArticlesStateEdit
import com.nikol.transaction.screens.edit.stateHoisting.EditTransactionScreenAction
import com.nikol.transaction.screens.edit.stateHoisting.EditTransactionScreenEffect
import com.nikol.transaction.screens.edit.stateHoisting.EditTransactionScreenState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

internal class EditTransactionScreenViewModel(
    private val id: Int,
    private val transactionType: TransactionType,
    private val getTransactionByIdUseCase: GetTransactionByIdUseCase,
    private val getFilteredArticlesUseCase: GetFilteredArticlesUseCase,
    private val getAllAccountUseCase: GetAllAccountUseCase,
    private val updateTransactionUseCase: UpdateTransactionUseCase,
    private val deleteTransactionUseCase: DeleteTransactionUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<EditTransactionScreenState>(EditTransactionScreenState.Loading)
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<EditTransactionScreenEffect>()
    val effect = _effect.asSharedFlow()

    init {
        loadTransaction()
    }

    fun onAction(action: EditTransactionScreenAction) {
        when (action) {
            EditTransactionScreenAction.NavigateBack -> emitEffect(EditTransactionScreenEffect.NavigateBack)

            is EditTransactionScreenAction.SelectAccount -> loadAccounts()
            is EditTransactionScreenAction.SelectArticles -> loadArticles()
            is EditTransactionScreenAction.UpdateTransaction -> updateTransaction()
            is EditTransactionScreenAction.SelectAmount -> selectAmount(action.amount)
            is EditTransactionScreenAction.SelectDate -> emitEffect(EditTransactionScreenEffect.OpenPickDateDialog)
            is EditTransactionScreenAction.SelectTime -> emitEffect(EditTransactionScreenEffect.OpenPickTimeDialog)
            is EditTransactionScreenAction.UpdateComment -> updateComment(action.comment)
            is EditTransactionScreenAction.EditAccount -> editAccount(action.account)
            is EditTransactionScreenAction.EditArticles -> editArticles(action.articles)
            is EditTransactionScreenAction.EditAmount -> updateAmount(action.amount)
            is EditTransactionScreenAction.EditDate -> editDate(action.date)
            is EditTransactionScreenAction.EditTime -> editTime(action.time)
            is EditTransactionScreenAction.DeleteTransaction -> deleteTransaction()
        }
    }

    private fun loadTransaction() = viewModelScope.launch(Dispatchers.IO) {
        val result = getTransactionByIdUseCase(id)
        val newState = when (result) {
            is DetailTransactionState.Success -> EditTransactionScreenState.Content(transaction = result.transaction.toUi())
            else -> EditTransactionScreenState.Error
        }
        _state.value = newState
    }

    private fun loadAccounts() = withStateContent { st ->
        _state.value = st.copy(accountsStateEdit = AccountsStateEdit.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            emitEffect(EditTransactionScreenEffect.OpenModalBottomSheetWithAccounts)
            val result = getAllAccountUseCase()
            val newState = when (result) {
                is AccountState.Success -> st.copy(accountsStateEdit = AccountsStateEdit.Content(result.items))
                else -> st.copy(accountsStateEdit = AccountsStateEdit.Error)
            }
            _state.value = newState
        }
    }

    private fun loadArticles() = withStateContent { st ->
        _state.value = st.copy(articlesStateEdit = ArticlesStateEdit.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            emitEffect(EditTransactionScreenEffect.OpenModalBottomSheetWithArticles)
            val result = getFilteredArticlesUseCase(transactionType)
            val newState = when (result) {
                is ArticlesState.Success -> st.copy(articlesStateEdit = ArticlesStateEdit.Content(result.items))
                else -> st.copy(articlesStateEdit = ArticlesStateEdit.Error)
            }
            _state.value = newState
        }
    }

    private fun updateTransaction() = withStateContent { st ->
        _state.value = EditTransactionScreenState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = updateTransactionUseCase(
                id = id,
                transaction = UpdateTransaction(
                    accountId = st.transaction.accountId ?: 0,
                    categoryId = st.transaction.articlesId ?: 0,
                    amount = st.transaction.amount ?: 2,
                    transactionDate = st.transaction.dateTime,
                    comment = st.transaction.comment.orEmpty()
                )
            )
            when (result) {
                is UpdateTransactionState.Success -> emitEffect(EditTransactionScreenEffect.SuccessfulUpdate)
                is UpdateTransactionState.NotFound -> emitEffect(EditTransactionScreenEffect.ShowNotFoundDialog)
                else -> _state.value = st
            }
        }
    }

    private fun deleteTransaction() = withStateContent { st ->
        _state.value = EditTransactionScreenState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = deleteTransactionUseCase(id)
            when (result) {
                is DeleteTransactionState.Success -> emitEffect(EditTransactionScreenEffect.SuccessfulDelete)
                is DeleteTransactionState.NotFound -> {
                    emitEffect(EditTransactionScreenEffect.ShowNotFoundDialog)
                    _state.value = st
                }
                else -> _state.value = st
            }
        }
    }

    private fun updateComment(comment: String) = updateTransactionField { it.copy(comment = comment) }

    private fun editDate(date: LocalDate) = updateTransactionField {
        val currentTime = it.dateTime.toLocalTime()
        it.copy(dateTime = LocalDateTime.of(date, currentTime))
    }

    private fun editTime(time: LocalTime) = updateTransactionField {
        val currentDate = it.dateTime.toLocalDate()
        it.copy(dateTime = LocalDateTime.of(currentDate, time))
    }

    private fun updateAmount(amount: Int) = updateTransactionField { it.copy(amount = amount) }

    private fun editAccount(account: Account) = withStateContent { st ->
        val updatedTransaction = st.transaction.copy(accountName = account.name, accountId = account.id)
        _state.value = st.copy(transaction = updatedTransaction)
        emitEffect(EditTransactionScreenEffect.CloseModalBottomSheetWithAccount)
    }

    private fun editArticles(articles: Articles) = withStateContent { st ->
        val updatedTransaction = st.transaction.copy(articlesId = articles.id, articlesName = articles.name)
        _state.value = st.copy(transaction = updatedTransaction)
        emitEffect(EditTransactionScreenEffect.CloseModalBottomSheetWithArticles)
    }

    private fun selectAmount(amount: Int) {
        viewModelScope.launch {
            _effect.emit(EditTransactionScreenEffect.OpenDialogWithAmount(amount))
        }
    }

    private inline fun withStateContent(block: (EditTransactionScreenState.Content) -> Unit) {
        val st = _state.value as? EditTransactionScreenState.Content ?: return
        block(st)
    }

    private fun updateTransactionField(update: (TransactionAddUi) -> TransactionAddUi) {
        withStateContent { st ->
            _state.value = st.copy(transaction = update(st.transaction))
        }
    }

    private fun emitEffect(effect: EditTransactionScreenEffect) = viewModelScope.launch {
        _effect.emit(effect)
    }

    class Factory @AssistedInject constructor(
        @Assisted private val id: Int,
        @Assisted private val transactionType: TransactionType,
        private val getTransactionByIdUseCase: GetTransactionByIdUseCase,
        private val getFilteredArticlesUseCase: GetFilteredArticlesUseCase,
        private val getAllAccountUseCase: GetAllAccountUseCase,
        private val updateTransactionUseCase: UpdateTransactionUseCase,
        private val deleteTransactionUseCase: DeleteTransactionUseCase
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EditTransactionScreenViewModel(
                id,
                transactionType,
                getTransactionByIdUseCase,
                getFilteredArticlesUseCase,
                getAllAccountUseCase,
                updateTransactionUseCase,
                deleteTransactionUseCase
            ) as T
        }

        @AssistedFactory
        interface Factory {
            fun create(id: Int, transactionType: TransactionType): EditTransactionScreenViewModel.Factory
        }
    }
}
