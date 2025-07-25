package com.nikol.settings.secret

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import javax.inject.Inject


val Context.pinDataStore: DataStore<Preferences> by preferencesDataStore(name = "pin_store")


class EncryptedPinStorage @Inject constructor(private val context: Context) {

    private val dataStore = context.pinDataStore
    private val PIN_KEY = stringPreferencesKey("encrypted_pin")

    suspend fun savePin(pin: String) {
        require(pin.length == 4 && pin.all { it.isDigit() }) {
            "PIN-код должен состоять из 4 цифр"
        }

        val encrypted = CryptoHelper.encryptPin(pin)

        dataStore.edit { prefs ->
            prefs[PIN_KEY] = encrypted
        }
    }

    suspend fun isPinCorrect(input: String): Boolean {
        val encrypted = dataStore.data.first()[PIN_KEY] ?: return false

        return try {
            val decrypted = CryptoHelper.decryptPin(encrypted)
            decrypted == input
        } catch (e: Exception) {
            false
        }
    }

    suspend fun hasPin(): Boolean {
        return dataStore.data.first()[PIN_KEY] != null
    }

    suspend fun clearPin() {
        dataStore.edit { prefs ->
            prefs.remove(PIN_KEY)
        }
    }
}
