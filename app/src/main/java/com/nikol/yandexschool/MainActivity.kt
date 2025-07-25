package com.nikol.yandexschool

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nikol.settings.screens.SettingsViewModel
import com.nikol.settings.screens.pincode.PinCodeViewModel
import com.nikol.settings.theme.ThemeMode
import com.nikol.settings.vibrator.VibratorController
import com.nikol.ui.locale.LocalAppLocale
import com.nikol.ui.locale.applyLocale
import com.nikol.ui.theme.YandexSchoolTheme
import com.nikol.yandexschool.di.DaggerNavigationComponent
import com.nikol.yandexschool.di.appComponent
import com.nikol.yandexschool.ui.FinancialDetectiveApp
import com.nikol.yandexschool.ui.pinCode.PinCodeScreen
import com.nikol.yandexschool.ui.splash.SplashScreen
import com.nikol.yandexschool.ui.splash.SplashScreenViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appComponent = applicationContext.appComponent

        val navigationComponent = DaggerNavigationComponent.factory()
            .create(appComponent)

        enableEdgeToEdge()

        setContent {
            val listFeature = navigationComponent.featureNavigationApis.toList()
            val settingsViewModel = viewModel<SettingsViewModel>(
                factory = appComponent.settingsFeatureComponentFactory().create()
            )
            val pinCodeViewModel = viewModel<PinCodeViewModel>(
                factory = appComponent.settingsPinCodeFactory()
            )

            val themeMode = settingsViewModel.themeMode.collectAsStateWithLifecycle()
            val colorScheme = settingsViewModel.colorScheme.collectAsStateWithLifecycle()
            val isDarkTheme = when (themeMode.value) {
                ThemeMode.Dark -> true
                ThemeMode.Light -> false
                ThemeMode.System -> isSystemInDarkTheme()
            }

            val vibrationEnabled = settingsViewModel.vibrationEnabled.collectAsStateWithLifecycle()
            val vibrationEffect = settingsViewModel.vibrationEffect.collectAsStateWithLifecycle()
            val vibratorController = remember { VibratorController(this) }

            val hasPin by pinCodeViewModel.pinCodeHasPin.collectAsStateWithLifecycle()
            val pinVerified = remember { mutableStateOf(false) }

            val locale by settingsViewModel.locale.collectAsStateWithLifecycle()
            val localizedContext = remember(locale) {
                applicationContext.applyLocale(locale)
            }

            settingsViewModel.updateAppVersion(BuildConfig.VERSION_NAME)

            CompositionLocalProvider(
                LocalAppLocale provides locale,
                LocalContext provides localizedContext
            ) {
                YandexSchoolTheme(
                    darkTheme = isDarkTheme,
                    appColorScheme = colorScheme.value
                ) {
                    val splash = remember { mutableStateOf(true) }

                    when {
                        splash.value -> {
                            SplashScreen(
                                viewModel = viewModel<SplashScreenViewModel>(
                                    factory = appComponent.splashScreenViewModelFactory().create()
                                ),
                            ) {
                                splash.value = false
                            }
                        }

                        hasPin && !pinVerified.value -> {
                            PinCodeScreen(
                                onSuccess = {
                                    pinVerified.value = true
                                },
                                viewModel = pinCodeViewModel
                            )
                        }

                        else -> {
                            FinancialDetectiveApp(
                                listFeature = listFeature,
                                vibrationEnabled = vibrationEnabled.value,
                                vibrationEffect = vibrationEffect.value,
                                vibratorController = vibratorController
                            )
                        }
                    }
                }
            }
        }
    }
}
