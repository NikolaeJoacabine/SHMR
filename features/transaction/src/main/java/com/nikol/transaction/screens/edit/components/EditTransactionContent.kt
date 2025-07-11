package com.nikol.transaction.screens.edit.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nikol.transaction.R
import com.nikol.transaction.screens.edit.stateHoisting.EditTransactionScreenAction
import com.nikol.transaction.screens.edit.stateHoisting.EditTransactionScreenState
import com.nikol.ui.customUiComponents.ChevronRight
import com.nikol.ui.customUiComponents.CustomListItem
import com.nikol.ui.utils.formatAmountToUi
import java.time.format.DateTimeFormatter

@Composable
internal fun EditTransactionContent(
    state: EditTransactionScreenState.Content,
    onAction: (EditTransactionScreenAction) -> Unit
) {
    val transaction = state.transaction

    Column {
        CustomListItem(
            modifier = Modifier
                .height(70.dp)
                .clickable { onAction(EditTransactionScreenAction.SelectAccount) }
                .padding(horizontal = 16.dp),
            content = { Text(stringResource(R.string.score)) },
            trailing = {
                transaction.accountName?.let { Text(it) }
                ChevronRight()
            }
        )

        CustomListItem(
            modifier = Modifier
                .height(70.dp)
                .clickable { onAction(EditTransactionScreenAction.SelectArticles) }
                .padding(horizontal = 16.dp),
            content = { Text(stringResource(R.string.articles)) },
            trailing = {
                transaction.articlesName?.let { Text(it) }
                ChevronRight()
            }
        )

        CustomListItem(
            modifier = Modifier
                .height(70.dp)
                .clickable {
                    onAction(EditTransactionScreenAction.SelectAmount(transaction.amount ?: 0))
                }
                .padding(horizontal = 16.dp),
            content = { Text(stringResource(R.string.amount)) },
            trailing = {
                transaction.amount?.let { Text(formatAmountToUi(it)) }
                ChevronRight()
            }
        )

        CustomListItem(
            modifier = Modifier
                .height(70.dp)
                .clickable { onAction(EditTransactionScreenAction.SelectDate) }
                .padding(horizontal = 16.dp),
            content = { Text(stringResource(R.string.date)) },
            trailing = {
                Text(
                    transaction.dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                )
                ChevronRight()
            }
        )

        CustomListItem(
            modifier = Modifier
                .height(70.dp)
                .clickable { onAction(EditTransactionScreenAction.SelectTime) }
                .padding(horizontal = 16.dp),
            content = { Text(stringResource(R.string.time)) },
            trailing = {
                Text(transaction.dateTime.format(DateTimeFormatter.ofPattern("HH:mm")))
                ChevronRight()
            }
        )

        TextField(
            value = transaction.comment ?: "",
            onValueChange = { onAction(EditTransactionScreenAction.UpdateComment(it)) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(stringResource(R.string.coment)) }
        )

        Spacer(Modifier.size(32.dp))

        Button(
            onClick = { onAction(EditTransactionScreenAction.DeleteTransaction) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text(stringResource(R.string.delete_account))
        }
    }
}
