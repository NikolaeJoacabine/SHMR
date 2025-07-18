package com.nikol.transaction.screens.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nikol.domain.model.CreateTransaction
import com.nikol.domain.model.TransactionType
import com.nikol.domain.state.AccountState
import com.nikol.domain.state.CreateTransactionState
import com.nikol.domain.useCase.CreateTransactionUseCase
import com.nikol.domain.useCase.GetAllAccountUseCase
import com.nikol.domain.useCase.GetFilteredArticlesUseCase
import com.nikol.transaction.models.TransactionAddUi
import com.nikol.transaction.screens.add.stateHoisting.AccountsState
import com.nikol.transaction.screens.add.stateHoisting.AddTransactionScreenAction
import com.nikol.transaction.screens.add.stateHoisting.AddTransactionScreenEffect
import com.nikol.transaction.screens.add.stateHoisting.AddTransactionScreenState
import com.nikol.transaction.screens.add.stateHoisting.ArticlesState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

internal class AddTransactionScreenViewModel(
    private val transactionType: TransactionType,
    private val getAllAccountUseCase: GetAllAccountUseCase,
    private val getFilteredArticlesUseCase: GetFilteredArticlesUseCase,
    private val createTransactionUseCase: CreateTransactionUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<AddTransactionScreenState>(
        AddTransactionScreenState.Content(
            transaction = TransactionAddUi(dateTime = LocalDateTime.now()),
            accounts = AccountsState.Loading,
            articles = ArticlesState.Loading
        )
    )
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<AddTransactionScreenEffect>()
    val effect = _effect.asSharedFlow()

    fun onAction(action: AddTransactionScreenAction) {
        when (action) {
            is AddTransactionScreenAction.AddTransaction -> handleAddTransaction()
            is AddTransactionScreenAction.EditAccount -> loadAccounts()
            is AddTransactionScreenAction.EditAmount -> openAmountDialog()
            is AddTransactionScreenAction.EditArticles -> loadArticles()
            is AddTransactionScreenAction.EditComment -> updateComment(action.comment)
            is AddTransactionScreenAction.EditDate -> openDatePicker()
            is AddTransactionScreenAction.EditTime -> openTimePicker()
            is AddTransactionScreenAction.NavigateBack -> navigateBack()
            is AddTransactionScreenAction.SelectAccount -> selectAccount(action.name, action.id)
            is AddTransactionScreenAction.SelectArticles -> selectArticles(action.name, action.id)
            is AddTransactionScreenAction.SelectAmount -> selectAmount(action.amount)
            is AddTransactionScreenAction.SelectDate -> selectDate(action.date)
            is AddTransactionScreenAction.SelectTime -> selectTime(action.time)
        }
    }

    private fun handleAddTransaction() {
        val currentState = _state.value as? AddTransactionScreenState.Content ?: return
        _state.value = AddTransactionScreenState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            val response = createTransactionUseCase(
                createTransaction = CreateTransaction(
                    accountId = currentState.transaction.accountId ?: 0,
                    categoryId = currentState.transaction.articlesId ?: 0,
                    amount = currentState.transaction.amount ?: 0,
                    transactionDate = currentState.transaction.dateTime,
                    comment = currentState.transaction.comment ?: ""
                )
            )
            withContext(Dispatchers.Main) {
                when (response) {
                    is CreateTransactionState.Success -> _effect.emit(AddTransactionScreenEffect.OnSuccessfulAddedTransaction)
                    is CreateTransactionState.NotFound -> _effect.emit(AddTransactionScreenEffect.OnShowNotFoundDialog)
                    is CreateTransactionState.Error, is CreateTransactionState.NetworkError -> _state.value = currentState
                    is CreateTransactionState.OfflineSaved -> _effect.emit(AddTransactionScreenEffect.OnOfflineSavedTransaction)
                }
            }
        }
    }

    private fun loadAccounts() {
        val currentState = _state.value as? AddTransactionScreenState.Content ?: return
        _state.value = currentState.copy(accounts = AccountsState.Loading)

        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) { _effect.emit(AddTransactionScreenEffect.OpenModalBottomSheetsWithAllAccount) }
            val result = getAllAccountUseCase()
            updateAccountsState(result, currentState)
        }
    }

    private suspend fun updateAccountsState(result: AccountState, oldState: AddTransactionScreenState.Content) {
        val newState = when (result) {
            is AccountState.Success -> oldState.copy(accounts = AccountsState.Content(result.items))
            is AccountState.Error -> oldState.copy(accounts = AccountsState.Error)
            is AccountState.NoInternet -> oldState.copy(accounts = AccountsState.NoInternet)
        }
        withContext(Dispatchers.Main) { _state.value = newState }
    }

    private fun loadArticles() {
        val currentState = _state.value as? AddTransactionScreenState.Content ?: return
        _state.value = currentState.copy(articles = ArticlesState.Loading)

        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) { _effect.emit(AddTransactionScreenEffect.OpenModalBottomSheetsWithAllArticles) }
            val result = getFilteredArticlesUseCase(transactionType)
            updateArticlesState(result, currentState)
        }
    }

    private suspend fun updateArticlesState(result: com.nikol.domain.state.ArticlesState, oldState: AddTransactionScreenState.Content) {
        val newState = when (result) {
            is com.nikol.domain.state.ArticlesState.Success -> oldState.copy(articles = ArticlesState.Content(result.items))
            is com.nikol.domain.state.ArticlesState.Error -> oldState.copy(articles = ArticlesState.Error)
            is com.nikol.domain.state.ArticlesState.NetworkError -> oldState.copy(articles = ArticlesState.NoInternet)
        }
        withContext(Dispatchers.Main) { _state.value = newState }
    }

    private fun openAmountDialog() {
        viewModelScope.launch { _effect.emit(AddTransactionScreenEffect.OpenAmountDialog) }
    }

    private fun openDatePicker() {
        viewModelScope.launch { _effect.emit(AddTransactionScreenEffect.OpenDatePickerDialog) }
    }

    private fun openTimePicker() {
        viewModelScope.launch { _effect.emit(AddTransactionScreenEffect.OpenTimePickerDialog) }
    }

    private fun navigateBack() {
        viewModelScope.launch { _effect.emit(AddTransactionScreenEffect.OnBack) }
    }

    private fun updateComment(comment: String) {
        val currentState = _state.value as? AddTransactionScreenState.Content ?: return
        _state.value = currentState.copy(transaction = currentState.transaction.copy(comment = comment))
    }

    private fun selectAccount(name: String, id: Int) {
        val currentState = _state.value as? AddTransactionScreenState.Content ?: return
        _state.value = currentState.copy(transaction = currentState.transaction.copy(accountName = name, accountId = id))
        viewModelScope.launch { _effect.emit(AddTransactionScreenEffect.CloseModalBottomSheetsWithAllAccount) }
    }

    private fun selectArticles(name: String, id: Int) {
        val currentState = _state.value as? AddTransactionScreenState.Content ?: return
        _state.value = currentState.copy(transaction = currentState.transaction.copy(articlesName = name, articlesId = id))
        viewModelScope.launch { _effect.emit(AddTransactionScreenEffect.CloseModalBottomSheetsWithAllArticles) }
    }

    private fun selectAmount(amount: String) {
        val currentState = _state.value as? AddTransactionScreenState.Content ?: return
        _state.value = currentState.copy(transaction = currentState.transaction.copy(amount = amount.toIntOrNull()))
        viewModelScope.launch { _effect.emit(AddTransactionScreenEffect.CloseAmountDialog) }
    }

    private fun selectDate(date: LocalDate) {
        val currentState = _state.value as? AddTransactionScreenState.Content ?: return
        val oldDateTime = currentState.transaction.dateTime
        val newDateTime = LocalDateTime.of(date, oldDateTime.toLocalTime())
        _state.value = currentState.copy(transaction = currentState.transaction.copy(dateTime = newDateTime))
    }

    private fun selectTime(time: LocalTime) {
        val currentState = _state.value as? AddTransactionScreenState.Content ?: return
        val oldDateTime = currentState.transaction.dateTime
        val newDateTime = LocalDateTime.of(oldDateTime.toLocalDate(), time)
        _state.value = currentState.copy(transaction = currentState.transaction.copy(dateTime = newDateTime))
    }

    class Factory @AssistedInject constructor(
        @Assisted private val transactionType: TransactionType,
        private val getAllAccountUseCase: GetAllAccountUseCase,
        private val getFilteredArticlesUseCase: GetFilteredArticlesUseCase,
        private val createTransactionUseCase: CreateTransactionUseCase
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AddTransactionScreenViewModel(
                transactionType,
                getAllAccountUseCase,
                getFilteredArticlesUseCase,
                createTransactionUseCase
            ) as T
        }

        @AssistedFactory
        interface Factory {
            fun create(transactionType: TransactionType): AddTransactionScreenViewModel.Factory
        }
    }
}
