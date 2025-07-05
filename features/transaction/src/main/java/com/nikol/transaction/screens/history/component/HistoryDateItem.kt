package com.nikol.transaction.screens.history.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nikol.ui.customUiComponents.CustomListItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun HistoryDateItem(title: String, date: LocalDate, onClick: () -> Unit) {
    CustomListItem(
        modifier = Modifier
            .height(56.dp)
            .clickable(onClick = onClick)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 16.dp),
        content = { Text(title) },
        trailing = {
            Text(date.format(DateTimeFormatter.ofPattern("d MMMM yyyy")))
        }
    )
}
