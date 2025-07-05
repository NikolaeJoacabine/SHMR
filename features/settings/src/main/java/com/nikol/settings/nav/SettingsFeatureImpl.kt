package com.nikol.settings.nav

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.nikol.di.FeatureDependencies
import com.nikol.navigation.FeatureApi
import com.nikol.navigation.SettingsGraph
import com.nikol.settings.di.DaggerSettingsFeatureComponent
import com.nikol.settings.screens.setings.SettingsScreen

class SettingsFeatureImpl(
    private val dependencies: FeatureDependencies
) : FeatureApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        val featureComponent = DaggerSettingsFeatureComponent.factory()
            .create(dependencies)

        navGraphBuilder.navigation<SettingsGraph>(
            startDestination = Settings
        ) {
            composable<Settings> {
                SettingsScreen()
            }
        }
    }
}
