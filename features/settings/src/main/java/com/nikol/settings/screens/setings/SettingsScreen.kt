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
import com.nikol.settings.R
import com.nikol.settings.theme.ThemeMode
import com.nikol.settings.screens.SettingsViewModel

import com.nikol.settings.screens.setings.components.SettingsOptionList
import com.nikol.settings.screens.setings.components.ThemeSwitcherItem
import com.nikol.ui.customUiComponents.DefaultTopBar
import org.intellij.lang.annotations.Language

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onClickColor: () -> Unit,
    onClickVibrator: () -> Unit,
    onClickLanguage: () -> Unit,
    onClickAddDetail: () -> Unit,
    onclickWorker: () -> Unit,
    onClickPinCode: () -> Unit
) {
    val scrollState = rememberScrollState()
    val themeMode = viewModel.themeMode.collectAsStateWithLifecycle()

    val isDarkTheme = themeMode.value == ThemeMode.Dark

    val list = listOf(
        stringResource(R.string.primary_color),
        stringResource(R.string.haptics),
        stringResource(R.string.passcode),
        stringResource(R.string.sync),
        stringResource(R.string.language),
        stringResource(R.string.about)
    )

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
                options = list,
                onClick = { option ->
                    when (option){
                        list[0] -> onClickColor()
                        list[1] -> onClickVibrator()
                        list[2] -> onClickPinCode()
                        list[3] -> onclickWorker()
                        list[4] -> onClickLanguage()
                        list[5] -> onClickAddDetail()
                    }
                }
            )
        }
    }
}
