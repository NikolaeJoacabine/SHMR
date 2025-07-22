package com.nikol.settings.screens.setings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nikol.settings.ThemeMode
import com.nikol.settings.R
import com.nikol.settings.screens.setings.components.SettingsOptionList
import com.nikol.settings.screens.setings.components.ThemeSwitcherItem
import com.nikol.ui.customUiComponents.DefaultTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {
    val scrollState = rememberScrollState()
    val themeMode = viewModel.themeMode.collectAsStateWithLifecycle()

    val isDarkTheme = themeMode.value == ThemeMode.Dark

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
            ThemeSwitcherItem(
                isDarkTheme = isDarkTheme,
                onToggle = { checked ->
                    val newMode = if (checked) ThemeMode.Dark else ThemeMode.Light
                    viewModel.setTheme(newMode)
                }
            )

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
