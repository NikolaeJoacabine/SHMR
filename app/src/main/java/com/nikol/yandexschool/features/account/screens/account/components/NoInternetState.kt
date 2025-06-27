package com.nikol.yandexschool.features.account.screens.account.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nikol.yandexschool.ui.customUiComponents.NoInternet


@Composable
fun NoInternetState(
    padding: PaddingValues,
    onRetryClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        NoInternet(onClick = onRetryClick)
    }
}
