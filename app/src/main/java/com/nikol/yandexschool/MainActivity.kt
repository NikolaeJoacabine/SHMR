package com.nikol.yandexschool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nikol.settings.ThemeMode
import com.nikol.settings.screens.setings.SettingsViewModel
import com.nikol.ui.theme.YandexSchoolTheme
import com.nikol.yandexschool.di.DaggerNavigationComponent
import com.nikol.yandexschool.di.appComponent
import com.nikol.yandexschool.ui.FinancialDetectiveApp
import com.nikol.yandexschool.ui.splash.SplashScreen
import com.nikol.yandexschool.ui.splash.SplashScreenViewModel

class MainActivity : ComponentActivity() {
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

            val themeMode = settingsViewModel.themeMode.collectAsStateWithLifecycle()

            val isDarkTheme = when (themeMode.value) {
                ThemeMode.Dark -> true
                ThemeMode.Light -> false
                ThemeMode.System -> isSystemInDarkTheme()
            }

            YandexSchoolTheme(isDarkTheme) {

                val splash = remember { mutableStateOf(true) }


                Crossfade(
                    targetState = splash.value,
                    label = "SplashToAppTransition"
                ) { isSplash ->
                    if (isSplash) {
                        SplashScreen(
                            viewModel = viewModel<SplashScreenViewModel>(
                                factory = appComponent.splashScreenViewModelFactory().create()
                            ),
                        ) {
                            splash.value = false
                        }
                    } else {
                        FinancialDetectiveApp(listFeature = listFeature)
                    }
                }
            }
        }
    }
}
