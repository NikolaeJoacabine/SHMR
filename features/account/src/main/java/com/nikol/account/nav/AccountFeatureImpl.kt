package com.nikol.account.nav

import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.nikol.account.di.DaggerAccountFeatureComponent
import com.nikol.account.screens.account.AccountScreen
import com.nikol.account.screens.account.AccountScreenViewModel
import com.nikol.account.screens.account.di.DaggerAccountScreenComponent
import com.nikol.account.screens.edit.EditAccountScreen
import com.nikol.account.screens.edit.EditScreenViewModel
import com.nikol.account.screens.edit.di.DaggerEditScreenComponent
import com.nikol.di.FeatureDependencies
import com.nikol.navigation.AccountGraph
import com.nikol.navigation.FeatureApi

class AccountFeatureImpl(
    private val dependencies: FeatureDependencies
) : FeatureApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        val featureComponent = DaggerAccountFeatureComponent.factory().create(dependencies)

        val accountComponent = DaggerAccountScreenComponent.factory().create(featureComponent)
        val editComponent = DaggerEditScreenComponent.factory().create(featureComponent)

        navGraphBuilder.navigation<AccountGraph>(
            startDestination = AccountScreen
        ) {
            composable<AccountScreen> {
                val viewModel = viewModel<AccountScreenViewModel>(
                    factory = accountComponent.viewModelFactory().create()
                )
                AccountScreen(
                    viewModel = viewModel,
                    navigateToEditScreen = { id, name ->
                        navController.navigate(EditAccountScreen(id = id, name = name))
                    }
                )
            }
            composable<EditAccountScreen> {
                val (id, name) = it.toRoute<EditAccountScreen>()

                val viewModel = viewModel<EditScreenViewModel>(
                    factory = editComponent.viewModelFactory().create(id)
                )

                EditAccountScreen(
                    viewModel = viewModel,
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
