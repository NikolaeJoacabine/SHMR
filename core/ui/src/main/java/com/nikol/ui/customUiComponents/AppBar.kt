package com.nikol.ui.customUiComponents

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.nikol.ui.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionTopBar(title: String, onClick: () -> Unit) {
    CenterAlignedTopAppBar(
        title = { Text(text = title + " ${stringResource(R.string.today)}") },
        actions = {
            IconButton(onClick = onClick) {
                Icon(painterResource(R.drawable.history), contentDescription = "history")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountTopBar(onClick: () -> Unit) {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(R.string.my_account)) },
        actions = {
            IconButton(onClick = onClick) {
                Icon(Icons.Default.Create, contentDescription = "create")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopBar(text: String) {
    CenterAlignedTopAppBar(
        title = { Text(text = text) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}
