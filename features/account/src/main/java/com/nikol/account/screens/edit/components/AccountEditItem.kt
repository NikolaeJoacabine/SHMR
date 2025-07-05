package com.nikol.account.screens.edit.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.nikol.account.screens.edit.stateHoisting.EditScreenState
import com.nikol.ui.customUiComponents.CustomListItem
import com.nikol.ui.customUiComponents.shimmerEffect

@Composable
internal fun AccountEditItem(
    modifier: Modifier = Modifier,
    accountState: EditScreenState,
    onNameChange: (String) -> Unit,
    onBalanceChange: (String) -> Unit
) {
    CustomListItem(
        modifier = modifier,
        lead = {
            Icon(
                imageVector = Icons.Outlined.Person,
                contentDescription = null
            )
        },
        content = {
            when (accountState) {
                is EditScreenState.Loading -> {
                    Box(
                        modifier = Modifier
                            .width(150.dp)
                            .height(25.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .shimmerEffect()
                    )
                }

                is EditScreenState.Content -> {
                    AlwaysEditableTextField(
                        text = accountState.account.name,
                        onTextChanged = onNameChange
                    )
                }

                else -> Unit
            }
        },
        trailing = {
            when (accountState) {
                is EditScreenState.Loading -> {
                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(25.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .shimmerEffect()
                    )
                }

                is EditScreenState.Content -> {
                    Row {
                        FormattedAmountTextField(
                            text = accountState.account.balance,
                            onTextChanged = onBalanceChange
                        )
                        Text(" " + accountState.currencyType.str)
                    }
                }

                else -> Unit
            }
        }
    )
}
