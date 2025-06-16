package com.nikol.yandexschool.features.settings.nav

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.nikol.yandexschool.features.FeatureApi
import com.nikol.yandexschool.features.settings.screens.SettingsScreen
import com.nikol.yandexschool.ui.nav.Settings

class SettingsFeatureApi : FeatureApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable<Settings> { SettingsScreen() }
    }
}