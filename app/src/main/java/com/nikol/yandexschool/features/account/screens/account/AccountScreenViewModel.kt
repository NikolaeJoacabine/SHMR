package com.nikol.yandexschool.features.account.screens.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nikol.domain.state.AccountState
import com.nikol.domain.useCase.GetAccountUseCase
import com.nikol.yandexschool.features.account.screens.account.stait_hoisting.AccountScreenAction
import com.nikol.yandexschool.features.account.screens.account.stait_hoisting.AccountScreenEffect
import com.nikol.yandexschool.features.account.screens.account.stait_hoisting.AccountScreensState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AccountScreenViewModel(
    private val getAccountUseCase: GetAccountUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<AccountScreensState>(AccountScreensState.Loading)
    val state: StateFlow<AccountScreensState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<AccountScreenEffect>()
    val effect = _effect.asSharedFlow()

    init {
        onLoading()
    }

    fun onAction(action: AccountScreenAction) {
        when (action) {
            AccountScreenAction.OnClickEditButton -> {

            }

            AccountScreenAction.OnScreenEntered -> {
                onLoading()
            }
        }
    }

    private fun onLoading() {
        _state.value = AccountScreensState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            getAccountUseCase().let { result ->
                when (result) {
                    is AccountState.Error -> {
                        _state.value = AccountScreensState.Error
                    }

                    is AccountState.Success -> {
                        if (result.items.isEmpty()) {
                            _state.value = AccountScreensState.Empty
                        } else {
                            _state.value = AccountScreensState.Content(result.items)
                        }
                    }
                }
            }
        }
    }

    class Factory(
        private val getAccountUseCase: GetAccountUseCase
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AccountScreenViewModel(getAccountUseCase) as T
        }
    }
}

class AccountScreenViewModelFactoryFactory(
    private val getAccountUseCase: GetAccountUseCase,
) {
    fun create(): AccountScreenViewModel.Factory {
        return AccountScreenViewModel.Factory(getAccountUseCase)
    }
}