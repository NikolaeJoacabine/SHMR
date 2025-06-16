package com.nikol.yandexschool.features.transaction.nav

import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.nikol.domain.model.TransactionType
import com.nikol.yandexschool.di.appComponent
import com.nikol.yandexschool.features.FeatureApi
import com.nikol.yandexschool.features.transaction.di.DaggerTransactionFeatureComponent
import com.nikol.yandexschool.features.transaction.screens.transaction.TransactionScreen
import com.nikol.yandexschool.features.transaction.screens.transaction.TransactionViewModel
import com.nikol.yandexschool.features.transaction.screens.transaction.di.DaggerTransactionScreenComponent
import com.nikol.yandexschool.ui.nav.Expenses
import com.nikol.yandexschool.ui.nav.Income

class TransactionFeatureApi : FeatureApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        val context = navController.context
        val appComponent = context.appComponent

        val transactionFeatureComponent = DaggerTransactionFeatureComponent.factory()
            .create(context, appComponent)

        val transactionScreenComponent = DaggerTransactionScreenComponent.factory()
            .create(context, transactionFeatureComponent)

        navGraphBuilder.composable<Expenses> {

            val viewModel = viewModel<TransactionViewModel>(
                factory = transactionScreenComponent.viewModelFactoryFactory().create()
            )
            TransactionScreen(transactionType = TransactionType.Expenses, viewModel = viewModel)

        }

        navGraphBuilder.composable<Income> {

            val viewModel = viewModel<TransactionViewModel>(
                factory = transactionScreenComponent.viewModelFactoryFactory().create()
            )
            TransactionScreen(transactionType = TransactionType.Income, viewModel = viewModel)
        }
    }
}
