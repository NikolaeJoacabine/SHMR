package com.nikol.yandexschool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.nikol.ui.theme.YandexSchoolTheme
import com.nikol.yandexschool.di.DaggerNavigationComponent
import com.nikol.yandexschool.di.appComponent
import com.nikol.yandexschool.ui.FinancialDetectiveApp

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
                FinancialDetectiveApp(listFeature = listFeature)
            }
        }
    }
}
