package com.nikol.transaction.screens.analysis.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nikol.ui.customUiComponents.CustomListItem
import com.nikol.ui.locale.LocalAppLocale
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
internal fun AnalysisDateItem(
    title: String,
    date: LocalDate,
    onClick: () -> Unit
) {

    CustomListItem(
        modifier = Modifier
            .height(57.dp)
            .padding(horizontal = 16.dp),
        content = {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        trailing = {
            AssistChip(
                onClick = { onClick() },
                label = {

                    val formatter =
                        DateTimeFormatter.ofPattern("d MMMM yyyy", LocalAppLocale.current)
                    Text(
                        text = date.format(formatter),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )

                },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
                shape = CircleShape,
                border = null
            )
        }
    )
}