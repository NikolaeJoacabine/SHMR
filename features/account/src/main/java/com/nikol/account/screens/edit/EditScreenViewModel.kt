package com.nikol.account.screens.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nikol.account.models.AccountUi
import com.nikol.account.models.utils.toUi
import com.nikol.account.screens.account.stateHoisting.AccountScreensState
import com.nikol.account.screens.edit.stateHoisting.EditScreenAction
import com.nikol.account.screens.edit.stateHoisting.EditScreenEffect
import com.nikol.account.screens.edit.stateHoisting.EditScreenState
import com.nikol.data.util.formater.formatAmount
import com.nikol.domain.model.AccountUpdateRequest
import com.nikol.domain.model.CurrencyType
import com.nikol.domain.state.AccountByIdState
import com.nikol.domain.state.AccountDeleteState
import com.nikol.domain.state.AccountEditState
import com.nikol.domain.useCase.DeleteAccountUseCase
import com.nikol.domain.useCase.EditAccountUseCase
import com.nikol.domain.useCase.GetAccountByIdUseCase
import com.nikol.domain.useCase.GetCurrentCurrencyUseCase
import com.nikol.ui.utils.formatAmountToUi
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class EditScreenViewModel(
    private val id: Int,
    private val getAccountByIdUseCase: GetAccountByIdUseCase,
    private val getCurrentCurrencyUseCase: GetCurrentCurrencyUseCase,
    private val editAccountUseCase: EditAccountUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<EditScreenState>(EditScreenState.Loading)
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<EditScreenEffect>()
    val effect = _effect.asSharedFlow()

    init {
        loadAccountData()
    }

    fun onAction(action: EditScreenAction) {
        when (action) {
            is EditScreenAction.OnClickDeleteAccount -> deleteAccount(action.id)
            is EditScreenAction.OnClickEditAccount -> editAccount(action.id, action.amount.toDouble(), action.name)
            is EditScreenAction.OnClickNavigateBack -> navigateBack()
            is EditScreenAction.OnChangeName -> changeName(action.name)
            is EditScreenAction.OnChangeAmount -> changeAmount(action.amount)
        }
    }

    private fun loadAccountData() {
        _state.value = EditScreenState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getAccountByIdUseCase(id)) {
                is AccountByIdState.Error,
                is AccountByIdState.NoInternet -> _state.value = EditScreenState.Error
                is AccountByIdState.Success -> {
                    val currency = getCurrentCurrencyUseCase()
                    _state.value = EditScreenState.Content(
                        account = result.item.toUi(),
                        currencyType = currency
                    )
                }
            }
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _effect.emit(EditScreenEffect.NavigateBack)
        }
    }

    private fun changeName(newName: String) {
        val currentState = _state.value as? EditScreenState.Content ?: return
        _state.value = currentState.copy(account = currentState.account.copy(name = newName))
    }

    private fun changeAmount(amountStr: String) {
        val currentState = _state.value as? EditScreenState.Content ?: return
        val amountInt = amountStr.takeIf { it.isNotEmpty() }?.toIntOrNull() ?: return
        val formattedAmount = formatAmountToUi(amountInt)
        _state.value = currentState.copy(account = currentState.account.copy(balance = formattedAmount))
    }

    private fun editAccount(id: Int, amount: Double, name: String) {
        _state.value = EditScreenState.OnEditing
        viewModelScope.launch(Dispatchers.IO) {
            when (editAccountUseCase(AccountUpdateRequest(name, amount), id)) {
                AccountEditState.Success -> emitEffect(EditScreenEffect.OnEditedAccount)
                AccountEditState.Error,
                AccountEditState.NoInternet -> _state.value = EditScreenState.Error
            }
        }
    }

    private fun deleteAccount(id: Int) {
        val currentContent = _state.value as? EditScreenState.Content ?: return
        _state.value = EditScreenState.OnDeleting

        viewModelScope.launch {
            when (val result = deleteAccountUseCase(id)) {
                AccountDeleteState.Success -> emitEffect(EditScreenEffect.OnDeletedAccount)
                AccountDeleteState.Conflict -> {
                    _effect.emit(EditScreenEffect.OnShowDialogError)
                    _state.value = currentContent
                }
                AccountDeleteState.Error,
                AccountDeleteState.NoInternet -> _state.value = EditScreenState.Error
            }
        }
    }

    private fun emitEffect(effect: EditScreenEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

    // Factory
    @Suppress("UNCHECKED_CAST")
    class Factory @AssistedInject constructor(
        @Assisted private val id: Int,
        private val getAccountByIdUseCase: GetAccountByIdUseCase,
        private val getCurrentCurrencyUseCase: GetCurrentCurrencyUseCase,
        private val editAccountUseCase: EditAccountUseCase,
        private val deleteAccountUseCase: DeleteAccountUseCase
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EditScreenViewModel(
                id = id,
                getAccountByIdUseCase = getAccountByIdUseCase,
                getCurrentCurrencyUseCase = getCurrentCurrencyUseCase,
                editAccountUseCase = editAccountUseCase,
                deleteAccountUseCase = deleteAccountUseCase
            ) as T
        }

        @AssistedFactory
        interface Factory {
            fun create(id: Int): EditScreenViewModel.Factory
        }
    }
}
