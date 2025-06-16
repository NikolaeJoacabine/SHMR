package com.nikol.yandexschool.features.account.nav

import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.nikol.yandexschool.di.appComponent
import com.nikol.yandexschool.features.FeatureApi
import com.nikol.yandexschool.features.account.di.DaggerAccountFeatureComponent
import com.nikol.yandexschool.features.account.screens.account.AccountScreen
import com.nikol.yandexschool.features.account.screens.account.AccountScreenViewModel
import com.nikol.yandexschool.features.account.screens.account.di.DaggerAccountScreenComponent
import com.nikol.yandexschool.ui.nav.Account

class AccountFeatureApi : FeatureApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        val context = navController.context
        val appComponent = context.appComponent

        val accountFeatureComponent = DaggerAccountFeatureComponent.factory()
            .create(context, appComponent)

        val accountScreenComponent = DaggerAccountScreenComponent.factory()
            .create(context, accountFeatureComponent)


        navGraphBuilder.composable<Account>{
            val viewModel = viewModel<AccountScreenViewModel>(
                factory = accountScreenComponent.viewModelFactoryFactory().create()
            )
            AccountScreen()
        }
    }
}