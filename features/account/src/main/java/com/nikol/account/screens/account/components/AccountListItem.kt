package com.nikol.account.screens.account.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nikol.account.models.AccountUi
import com.nikol.domain.model.CurrencyType
import com.nikol.ui.customUiComponents.ChevronRight
import com.nikol.ui.customUiComponents.CustomListItem
import com.nikol.ui.customUiComponents.EmojiIcon

@Composable
internal fun AccountListItem(
    account: AccountUi,
    currentCurrencyType: CurrencyType
) {
    CustomListItem(
        modifier = Modifier
            .height(57.dp)
            .clickable { }
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 16.dp),
        lead = { EmojiIcon(account.emoji, Color.White) },
        content = {
            Text(
                text = account.name,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        trailing = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${account.balance} ${currentCurrencyType.str}",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyLarge
                )
                ChevronRight()
            }
        }
    )
}
