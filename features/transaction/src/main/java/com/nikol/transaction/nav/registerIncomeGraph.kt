package com.nikol.transaction.nav

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.nikol.domain.model.TransactionType
import com.nikol.navigation.IncomeGraph
import com.nikol.transaction.screens.history.HistoryScreen
import com.nikol.transaction.screens.history.HistoryScreenViewModel
import com.nikol.transaction.screens.history.di.HistoryScreenComponent
import com.nikol.transaction.screens.income.IncomeScreen
import com.nikol.transaction.screens.income.IncomeScreenViewModel
import com.nikol.transaction.screens.income.di.IncomeScreenComponent

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
                factory = incomeComponent.viewModelFactory().create()
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
                factory = historyComponent.viewModelFactory()
                    .create(TransactionType.Income)
            )
            HistoryScreen(
                viewModel = viewModel,
                onNavigateAnalyticScreen = {},
                onNavigateBack = { navController.popBackStack() },
                onNavigateToEditTransactionScreen = {}
            )
        }
    }
}
