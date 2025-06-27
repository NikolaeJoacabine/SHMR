package com.nikol.yandexschool.features.settings.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.nikol.yandexschool.R
import com.nikol.yandexschool.features.settings.screens.components.SettingsOptionList
import com.nikol.yandexschool.features.settings.screens.components.ThemeSwitcherItem
import com.nikol.yandexschool.ui.customUiComponents.DefaultTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    val scrollState = rememberScrollState()
    val isDarkTheme = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            DefaultTopBar(text = stringResource(R.string.settings))
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding.calculateTopPadding())
                .verticalScroll(scrollState)
        ) {
            ThemeSwitcherItem(isDarkTheme)

            SettingsOptionList(
                options = listOf(
                    stringResource(R.string.primary_color),
                    stringResource(R.string.sounds),
                    stringResource(R.string.haptics),
                    stringResource(R.string.passcode),
                    stringResource(R.string.sync),
                    stringResource(R.string.language),
                    stringResource(R.string.about)
                ),
                onClick = { option ->

                }
            )
        }
    }
}
