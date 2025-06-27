package com.nikol.yandexschool.features.account.screens.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nikol.domain.state.AccountState
import com.nikol.domain.useCase.GetAccountUseCase
import com.nikol.yandexschool.features.account.screens.account.models.utils.toUi
import com.nikol.yandexschool.features.account.screens.account.stateHoisting.AccountScreenAction
import com.nikol.yandexschool.features.account.screens.account.stateHoisting.AccountScreenEffect
import com.nikol.yandexschool.features.account.screens.account.stateHoisting.AccountScreensState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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
            is AccountScreenAction.OnClickEditButton -> {

            }

            is AccountScreenAction.OnScreenEntered -> {
                onLoading()
            }

            is AccountScreenAction.OnRetryClicked -> {
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
                            _state.value =
                                AccountScreensState.Content(result.items.map { it.toUi() })
                        }
                    }

                    is AccountState.NoInternet -> {
                        _state.value = AccountScreensState.NoInternet
                    }
                }
            }
        }
    }

    /**
     * Фабрика для создания экземпляра [AccountScreenViewModel].
     *
     * @param getAccountUseCase UseCase для загрузки аккаунтов.
     */
    class Factory(
        private val getAccountUseCase: GetAccountUseCase
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AccountScreenViewModel(getAccountUseCase) as T
        }
    }
}

/**
 * Фабрика-фабрик для создания [AccountScreenViewModel.Factory].
 *
 * Используется в DI-компонентах для передачи зависимостей.
 *
 * @param getAccountUseCase UseCase для получения аккаунтов.
 */
class AccountScreenViewModelFactoryFactory(
    private val getAccountUseCase: GetAccountUseCase,
) {
    /**
     * Создает экземпляр [AccountScreenViewModel.Factory].
     */
    fun create(): AccountScreenViewModel.Factory {
        return AccountScreenViewModel.Factory(getAccountUseCase)
    }
}
