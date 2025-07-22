package com.nikol.settings.screens.setings.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nikol.settings.R
import com.nikol.ui.customUiComponents.CustomListItem

@Composable
fun ThemeSwitcherItem(
    isDarkTheme: Boolean,
    onToggle: (Boolean) -> Unit
) {
    CustomListItem(
        modifier = Modifier
            .height(55.dp)
            .padding(horizontal = 16.dp),
        content = { Text(stringResource(R.string.dark_theme)) },
        trailing = {
            Switch(
                checked = isDarkTheme,
                onCheckedChange = onToggle
            )
        }
    )
}
