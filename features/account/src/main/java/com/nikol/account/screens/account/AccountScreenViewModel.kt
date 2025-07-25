package com.nikol.account.screens.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nikol.account.models.utils.toUi
import com.nikol.account.screens.account.stateHoisting.AccountScreenAction
import com.nikol.account.screens.account.stateHoisting.AccountScreenEffect
import com.nikol.account.screens.account.stateHoisting.AccountScreenEffect.NavigateToEditScreen
import com.nikol.account.screens.account.stateHoisting.AccountScreensState
import com.nikol.domain.model.CurrencyType
import com.nikol.domain.state.AccountState
import com.nikol.domain.useCase.GetAccountUseCase
import com.nikol.domain.useCase.GetAllCurrencyUseCase
import com.nikol.domain.useCase.GetCurrentCurrencyUseCase
import com.nikol.domain.useCase.GetStatisticForAccount
import com.nikol.domain.useCase.SaveCurrencyUseCase
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel для экрана аккаунта.
 *
 * Отвечает за:
 * - Загрузку информации об аккаунте с помощью [GetAccountUseCase];
 * - Формирование соответствующего состояния экрана ([AccountScreensState]);
 * - Обработку пользовательских действий ([AccountScreenAction]).
 *
 * @param getAccountUseCase UseCase для получения списка аккаунтов.
 */
internal class AccountScreenViewModel(
    private val getAccountUseCase: GetAccountUseCase,
    private val getAllCurrencyUseCase: GetAllCurrencyUseCase,
    private val saveCurrencyUseCase: SaveCurrencyUseCase,
    private val getCurrentCurrencyUseCase: GetCurrentCurrencyUseCase,
    private val getStatisticForAccount: GetStatisticForAccount
) : ViewModel() {

    private val _state = MutableStateFlow<AccountScreensState>(AccountScreensState.Loading)
    val state: StateFlow<AccountScreensState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<AccountScreenEffect>()
    val effect: SharedFlow<AccountScreenEffect> = _effect.asSharedFlow()

    fun onAction(action: AccountScreenAction) {
        when (action) {
            is AccountScreenAction.OnScreenEntered,
            is AccountScreenAction.OnRetryClicked -> loadAccounts()

            is AccountScreenAction.OnClickEditButton -> navigateToEdit()

            is AccountScreenAction.OnClickToCurrentCurrency -> openCurrencyModal()

            is AccountScreenAction.OnClickToCloseModalBottomSheet -> closeCurrencyModal()

            is AccountScreenAction.OnClickToCurrency -> selectCurrency(action.currencyType)
        }
    }

    private fun loadAccounts() {
        _state.value = AccountScreensState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getAccountUseCase()) {
                is AccountState.Error -> _state.value = AccountScreensState.Error
                is AccountState.NoInternet -> _state.value = AccountScreensState.NoInternet
                is AccountState.Success -> {
                    if (result.items.isEmpty()) {
                        _state.value = AccountScreensState.Empty
                    } else {
                        val accounts = result.items.map { it.toUi() }
                        val currency = getCurrentCurrencyUseCase()
                        val analysis = getStatisticForAccount()
                        _state.value = AccountScreensState.Content(
                            list = accounts,
                            currentCurrency = currency,
                            listAnalysis = analysis
                        )
                    }
                }
            }
        }
    }

    private fun navigateToEdit() {
        val content = _state.value as? AccountScreensState.Content ?: return
        val firstAccount = content.list.firstOrNull() ?: return
        emitEffect(AccountScreenEffect.NavigateToEditScreen(firstAccount.id, firstAccount.name))
    }

    private fun openCurrencyModal() {
        viewModelScope.launch {
            emitEffect(AccountScreenEffect.OpenModalBottomSheet)

            val content = _state.value as? AccountScreensState.Content ?: return@launch
            val currencies = getAllCurrencyUseCase()
            _state.value = content.copy(currency = currencies)
        }
    }

    private fun closeCurrencyModal() {
        emitEffect(AccountScreenEffect.ClosedModalBottomSheet)
    }

    private fun selectCurrency(currencyType: CurrencyType) {
        val content = _state.value as? AccountScreensState.Content ?: return

        viewModelScope.launch(Dispatchers.IO) {
            _state.value = content.copy(currentCurrency = currencyType)
            saveCurrencyUseCase(currencyType)
            emitEffect(AccountScreenEffect.ClosedModalBottomSheet)
        }
    }

    private fun emitEffect(effect: AccountScreenEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

    // ViewModel Factory
    class Factory @AssistedInject constructor(
        private val getAccountUseCase: GetAccountUseCase,
        private val getAllCurrencyUseCase: GetAllCurrencyUseCase,
        private val saveCurrencyUseCase: SaveCurrencyUseCase,
        private val getCurrentCurrencyUseCase: GetCurrentCurrencyUseCase,
        private val getStatisticForAccount: GetStatisticForAccount
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AccountScreenViewModel(
                getAccountUseCase,
                getAllCurrencyUseCase,
                saveCurrencyUseCase,
                getCurrentCurrencyUseCase,
                getStatisticForAccount
            ) as T
        }

        @AssistedFactory
        interface Factory {
            fun create(): AccountScreenViewModel.Factory
        }
    }
}
