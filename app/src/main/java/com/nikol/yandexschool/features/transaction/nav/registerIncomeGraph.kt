package com.nikol.yandexschool.features.transaction.nav

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.nikol.domain.model.TransactionType
import com.nikol.yandexschool.features.transaction.screens.history.HistoryScreen
import com.nikol.yandexschool.features.transaction.screens.history.HistoryScreenViewModel
import com.nikol.yandexschool.features.transaction.screens.history.di.HistoryScreenComponent
import com.nikol.yandexschool.features.transaction.screens.income.IncomeScreen
import com.nikol.yandexschool.features.transaction.screens.income.IncomeScreenViewModel
import com.nikol.yandexschool.features.transaction.screens.income.di.IncomeScreenComponent
import com.nikol.yandexschool.ui.nav.Income
import com.nikol.yandexschool.ui.nav.IncomeGraph
import com.nikol.yandexschool.ui.nav.IncomeHistory

/**
 * Регистрирует навигационный граф для экрана доходов и его истории.
 *
 * @param navController Навигационный контроллер для управления навигацией.
 * @param incomeComponent Dagger-компонент, предоставляющий зависимости для экрана доходов.
 * @param historyComponent Dagger-компонент, предоставляющий зависимости для экрана истории транзакций.
 */
fun NavGraphBuilder.registerIncomeGraph(
    navController: NavHostController,
    incomeComponent: IncomeScreenComponent,
    historyComponent: HistoryScreenComponent
) {
    navigation<IncomeGraph>(startDestination = Income) {

        composable<Income> {
            val viewModel = viewModel<IncomeScreenViewModel>(
                factory = incomeComponent.viewModelFactoryFactory().create()
            )
            IncomeScreen(
                viewModel = viewModel,
                onNavigateToDetail = {},
                onNavigateToAdded = {},
                onNavigateToHistory = {
                    navController.navigate(IncomeHistory)
                }
            )
        }

        composable<IncomeHistory> {
            val viewModel = viewModel<HistoryScreenViewModel>(
                factory = historyComponent.viewModelFactoryFactory()
                    .create(TransactionType.Income)
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
