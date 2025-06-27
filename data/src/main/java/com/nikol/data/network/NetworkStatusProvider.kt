package com.nikol.data.network

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.annotation.RequiresPermission

/**
 * Провайдер статуса сети.
 * Предоставляет информацию о том, подключено ли устройство к интернету.
 */
interface NetworkStatusProvider {

    /**
     * Проверяет, есть ли у устройства активное интернет-соединение.
     *
     * @return `true`, если устройство подключено к интернету, `false` в противном случае.
     */
    fun isConnected(): Boolean
}

/**
 * Реализация [NetworkStatusProvider], использующая системные сервисы Android.
 *
 * @param context Контекст приложения, необходим для получения системных сервисов.
 */
class NetworkStatusProviderImpl(
    private val context: Context
) : NetworkStatusProvider {

    /**
     * Проверяет, есть ли у устройства активное интернет-соединение.
     * Требует разрешение `ACCESS_NETWORK_STATE`.
     *
     * @return `true`, если устройство подключено к интернету, `false` в противном случае.
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun isConnected(): Boolean {
        val cm = context.getSystemService(ConnectivityManager::class.java)
        val network = cm?.activeNetwork ?: return false
        val capabilities = cm.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}

