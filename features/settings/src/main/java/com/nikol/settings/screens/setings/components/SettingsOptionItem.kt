package com.nikol.settings.screens.setings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nikol.settings.R
import com.nikol.ui.customUiComponents.CustomListItem

@Composable
fun SettingsOptionItem(
    title: String,
    onClick: () -> Unit
) {
    CustomListItem(
        modifier = Modifier
            .height(55.dp)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        content = { Text(title) },
        trailing = {
            Icon(
                painter = painterResource(R.drawable.arrow_right),
                contentDescription = null
            )
        }
    )
}
