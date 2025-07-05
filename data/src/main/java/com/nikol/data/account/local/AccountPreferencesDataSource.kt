package com.nikol.data.account.local

import android.content.Context
import android.icu.util.Currency
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

/**
 * Отвечает за сохранение и получение идентификатора текущего аккаунта в локальном
 * хранилище настроек приложения.
 */
class AccountPreferencesDataSource(context: Context) {

    private val Context.dataStore by preferencesDataStore(name = "account_prefs")

    private val dataStore = context.dataStore

    companion object {
        private val CURRENT_ACCOUNT_ID = intPreferencesKey("current_account_id")
        private val CURRENT_CURRENCY = stringPreferencesKey("current_currency")
    }

    /**
     * Сохраняет текущий идентификатор аккаунта в DataStore.
     *
     * @param id идентификатор аккаунта для сохранения
     */
    suspend fun saveAccountId(id: Int) {
        dataStore.edit { prefs ->
            prefs[CURRENT_ACCOUNT_ID] = id
        }
    }

    /**
     * Получает текущий сохранённый идентификатор аккаунта из DataStore.
     *
     * @return сохранённый идентификатор аккаунта или null, если не найден
     */
    suspend fun getAccountId(): Int? {
        val prefs = dataStore.data.first()
        return prefs[CURRENT_ACCOUNT_ID]
    }

    /**
     * Сохраняет текущую валюту в DataStore.
     *
     * @param currency тип валюты (только RUB, USD, EUR)
     */
    suspend fun saveCurrentCurrency(currency: String){
        dataStore.edit { preferences ->
            preferences[CURRENT_CURRENCY] = currency
        }
    }

    /**
     * Получает текущую валюту из DataStore.
     *
     * @return сохранённая валюта или null, если не найдена
     */
    suspend fun getCurrentCurrency(): String?{
        val pref = dataStore.data.first()
        return pref[CURRENT_CURRENCY]
    }
}
