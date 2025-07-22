package com.nikol.settings.nav

import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.nikol.di.FeatureDependencies
import com.nikol.navigation.FeatureApi
import com.nikol.navigation.SettingsGraph
import com.nikol.settings.di.DaggerSettingsFeatureComponent
import com.nikol.settings.screens.setings.SettingsScreen
import com.nikol.settings.screens.setings.SettingsViewModel
import com.nikol.settings.screens.setings.di.DaggerSettingsScreenComponent

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
        val settingsComponent = DaggerSettingsScreenComponent.factory()
            .create(featureComponent)

        navGraphBuilder.navigation<SettingsGraph>(
            startDestination = Settings
        ) {
            composable<Settings> {
                val viewModel = viewModel<SettingsViewModel>(
                    factory = settingsComponent.viewModelFactory().create()
                )
                SettingsScreen(viewModel)
            }
        }
    }
}
