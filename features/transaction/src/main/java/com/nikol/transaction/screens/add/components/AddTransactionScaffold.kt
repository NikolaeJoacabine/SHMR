package com.nikol.transaction.screens.add.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.nikol.transaction.R
import com.nikol.transaction.screens.add.stateHoisting.AddTransactionScreenAction
import com.nikol.transaction.screens.add.stateHoisting.AddTransactionScreenState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddTransactionScaffold(
    state: AddTransactionScreenState,
    onAction: (AddTransactionScreenAction) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(R.string.adding_transaction)) },
                navigationIcon = {
                    IconButton(onClick = { onAction(AddTransactionScreenAction.NavigateBack) }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(top = paddingValues.calculateTopPadding())) {
            when (state) {
                is AddTransactionScreenState.Content -> AddTransactionScreenContent(
                    state = state,
                    onClickAccount = { onAction(AddTransactionScreenAction.EditAccount) },
                    onClickArticles = { onAction(AddTransactionScreenAction.EditArticles) },
                    onClickAmount = { onAction(AddTransactionScreenAction.EditAmount) },
                    onClickDate = { onAction(AddTransactionScreenAction.EditDate) },
                    onClickCreateTransaction = { onAction(AddTransactionScreenAction.AddTransaction) },
                    onEditText = { onAction(AddTransactionScreenAction.EditComment(it)) },
                    onClickTime = { onAction(AddTransactionScreenAction.EditTime) }
                )
                is AddTransactionScreenState.Loading -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
