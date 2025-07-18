package com.nikol.settings.screens.setings.components

import androidx.compose.runtime.Composable

@Composable
fun SettingsOptionList(
    options: List<String>,
    onClick: (String) -> Unit
) {
    options.forEach { title ->
        SettingsOptionItem(title = title, onClick = { onClick(title) })
    }
}
