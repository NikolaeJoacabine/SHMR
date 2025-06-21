package com.nikol.yandexschool.features.transaction.nav

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.nikol.domain.model.TransactionType
import com.nikol.yandexschool.di.appComponent
import com.nikol.yandexschool.features.FeatureApi
import com.nikol.yandexschool.features.transaction.di.DaggerTransactionFeatureComponent
import com.nikol.yandexschool.features.transaction.screens.history.di.DaggerHistoryScreenComponent
import com.nikol.yandexschool.features.transaction.screens.income.IncomeScreen
import com.nikol.yandexschool.features.transaction.screens.income.IncomeScreenViewModel
import com.nikol.yandexschool.features.transaction.screens.income.di.DaggerIncomeScreenComponent
import com.nikol.yandexschool.features.transaction.screens.expenses.ExpensesScreenViewModel
import com.nikol.yandexschool.features.transaction.screens.expenses.ExpensesScreen
import com.nikol.yandexschool.features.transaction.screens.expenses.di.DaggerExpensesScreenComponent
import com.nikol.yandexschool.features.transaction.screens.history.HistoryScreen
import com.nikol.yandexschool.features.transaction.screens.history.HistoryScreenViewModel

import com.nikol.yandexschool.ui.nav.Expenses
import com.nikol.yandexschool.ui.nav.ExpensesGraph
import com.nikol.yandexschool.ui.nav.ExpensesHistory
import com.nikol.yandexschool.ui.nav.Income
import com.nikol.yandexschool.ui.nav.IncomeGraph
import com.nikol.yandexschool.ui.nav.IncomeHistory

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

        val transactionScreenComponent = DaggerExpensesScreenComponent.factory()
            .create(context, transactionFeatureComponent)

        val incomeScreenComponent = DaggerIncomeScreenComponent.factory()
            .create(context, transactionFeatureComponent)

        val historyScreenComponent = DaggerHistoryScreenComponent.factory()
            .create(context, transactionFeatureComponent)

        navGraphBuilder.navigation<ExpensesGraph>(
            startDestination = Expenses
        ) {
            composable<Expenses> {
                val viewModel = viewModel<ExpensesScreenViewModel>(
                    factory = transactionScreenComponent.viewModelFactoryFactory().create()
                )
                ExpensesScreen(
                    viewModel = viewModel,
                    onNavigateToDetail = { },
                    onNavigateToAdded = { },
                    onNavigateToHistory = { navController.navigate(ExpensesHistory) }
                )
            }

            composable<ExpensesHistory> {

                val viewModel = viewModel<HistoryScreenViewModel>(
                    factory = historyScreenComponent.viewModelFactoryFactory().create(
                        TransactionType.Expenses
                    )
                )
                HistoryScreen(
                    viewModel = viewModel,
                    onNavigateAnalyticScreen = {},
                    onNavigateBack = { navController.popBackStack(Expenses, inclusive = false) },
                    onNavigateToEditTransactionScreen = {}
                )
            }
        }

        navGraphBuilder.navigation<IncomeGraph>(
            startDestination = Income
        ) {

            composable<Income> {
                val viewModel = viewModel<IncomeScreenViewModel>(
                    factory = incomeScreenComponent.viewModelFactoryFactory().create()
                )
                IncomeScreen(
                    viewModel = viewModel,
                    onNavigateToDetail = { },
                    onNavigateToHistory = { navController.navigate(IncomeHistory) },
                    onNavigateToAdded = { }
                )
            }

            composable<IncomeHistory> {

                val viewModel = viewModel<HistoryScreenViewModel>(
                    factory = historyScreenComponent.viewModelFactoryFactory().create(
                        TransactionType.Income
                    )
                )
                HistoryScreen(
                    viewModel = viewModel,
                    onNavigateAnalyticScreen = {},
                    onNavigateBack = { navController.popBackStack(Income, inclusive = false) },
                    onNavigateToEditTransactionScreen = {}
                )
            }
        }
    }
}
