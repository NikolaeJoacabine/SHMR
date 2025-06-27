package com.nikol.yandexschool.features.articles.screnns.articles.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nikol.yandexschool.ui.customUiComponents.NoInternet

@Composable
fun NoInternetState(onRetry: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        NoInternet(onClick = onRetry)
    }
}
