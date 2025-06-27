package com.nikol.yandexschool.features.transaction.screens.income.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nikol.yandexschool.ui.customUiComponents.CustomListItem

@Composable
fun IncomeTotalRow(total: String) {
    CustomListItem(
        modifier = Modifier
            .height(56.dp)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 16.dp),
        content = {
            Text("Всего", style = MaterialTheme.typography.bodyLarge)
        },
        trailing = {
            Text("$total ₽", style = MaterialTheme.typography.bodyLarge)
        }
    )
}
