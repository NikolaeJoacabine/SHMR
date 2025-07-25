package com.nikol.settings.screens.pincode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nikol.settings.secret.EncryptedPinStorage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PinCodeViewModel(
    private val encryptedPinStorage: EncryptedPinStorage
) : ViewModel() {

    val pinCodeHasPin = MutableStateFlow(false)

    private val _isPinCorrect = MutableStateFlow<Boolean?>(null)
    val isPinCorrect: StateFlow<Boolean?> = _isPinCorrect


    init {
        viewModelScope.launch {
            pinCodeHasPin.value = encryptedPinStorage.hasPin()
        }
    }

    fun checkPin(input: String) {
        viewModelScope.launch {
            try {
                val result = encryptedPinStorage.isPinCorrect(input)
                _isPinCorrect.value = result
                if (!result) {
                    delay(200)
                    _isPinCorrect.value = null
                }
            } catch (e: Exception) {
                _isPinCorrect.value = false
            }
        }
    }

    fun setPin(pin: String) {
        viewModelScope.launch {
            encryptedPinStorage.savePin(pin)
        }
    }

    fun clearPin() {
        viewModelScope.launch {
            encryptedPinStorage.clearPin()
        }
    }


    class Factory @Inject constructor(
        private val encryptedPinStorage: EncryptedPinStorage
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PinCodeViewModel(encryptedPinStorage) as T
        }
    }
}