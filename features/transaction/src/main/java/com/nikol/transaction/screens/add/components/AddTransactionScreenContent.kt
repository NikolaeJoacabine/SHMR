package com.nikol.transaction.screens.add.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nikol.transaction.R
import com.nikol.transaction.screens.add.stateHoisting.AddTransactionScreenState
import com.nikol.ui.customUiComponents.ChevronRight
import com.nikol.ui.customUiComponents.CustomListItem
import java.time.format.DateTimeFormatter

@Composable
internal fun AddTransactionScreenContent(
    state: AddTransactionScreenState.Content,
    onClickAccount: () -> Unit,
    onClickArticles: () -> Unit,
    onClickAmount: () -> Unit,
    onClickDate: () -> Unit,
    onClickCreateTransaction: () -> Unit,
    onEditText: (String) -> Unit,
    onClickTime: () -> Unit
) {
    CustomListItem(
        modifier = Modifier
            .height(70.dp)
            .clickable { onClickAccount() }
            .padding(horizontal = 16.dp),
        content = { Text(text = stringResource(R.string.score)) },
        trailing = {
            state.transaction.accountName?.let { Text(it) }
            ChevronRight()
        }
    )

    CustomListItem(
        modifier = Modifier
            .height(70.dp)
            .clickable { onClickArticles() }
            .padding(horizontal = 16.dp),
        content = { Text(text = stringResource(R.string.articles)) },
        trailing = {
            state.transaction.articlesName?.let { Text(it) }
            ChevronRight()
        }
    )

    CustomListItem(
        modifier = Modifier
            .height(70.dp)
            .clickable { onClickAmount() }
            .padding(horizontal = 16.dp),
        content = { Text(text = stringResource(R.string.amount)) },
        trailing = {
            state.transaction.amount?.let { Text(it.toString()) }
            ChevronRight()
        }
    )

    CustomListItem(
        modifier = Modifier
            .height(70.dp)
            .clickable { onClickDate() }
            .padding(horizontal = 16.dp),
        content = { Text(stringResource(R.string.date)) },
        trailing = {
            Text(state.transaction.dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
            ChevronRight()
        }
    )

    CustomListItem(
        modifier = Modifier
            .height(70.dp)
            .clickable { onClickTime() }
            .padding(horizontal = 16.dp),
        content = { Text(stringResource(R.string.time)) },
        trailing = {
            Text(state.transaction.dateTime.format(DateTimeFormatter.ofPattern("HH:mm")))
            ChevronRight()
        }
    )

    val comment = state.transaction.comment.orEmpty()
    val isFormValid = state.transaction.run {
        !accountName.isNullOrBlank()
                && !articlesName.isNullOrBlank()
                && amount != null
    }

    TextField(
        value = comment,
        onValueChange = onEditText,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(stringResource(R.string.coment)) }
    )
    Spacer(Modifier.size(32.dp))
    Button(
        onClick = onClickCreateTransaction,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        enabled = isFormValid
    ) {
        Text(stringResource(R.string.create_transaction))
    }
}
