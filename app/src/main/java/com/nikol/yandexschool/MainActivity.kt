package com.nikol.yandexschool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
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
            YandexSchoolTheme {
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
