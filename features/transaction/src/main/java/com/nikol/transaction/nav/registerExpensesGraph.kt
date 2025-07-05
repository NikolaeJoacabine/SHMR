package com.nikol.transaction.nav

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.nikol.domain.model.TransactionType
import com.nikol.navigation.ExpenseGraph
import com.nikol.transaction.screens.expenses.ExpensesScreen
import com.nikol.transaction.screens.expenses.ExpensesScreenViewModel
import com.nikol.transaction.screens.expenses.di.ExpensesScreenComponent
import com.nikol.transaction.screens.history.HistoryScreen
import com.nikol.transaction.screens.history.HistoryScreenViewModel
import com.nikol.transaction.screens.history.di.HistoryScreenComponent

/**
 * Регистрирует навигационный граф для экрана расходов и его истории.
 *
 * @param navController Навигационный контроллер для управления навигацией.
 * @param expensesComponent Dagger-компонент, предоставляющий зависимости для экрана расходов.
 * @param historyComponent Dagger-компонент, предоставляющий зависимости для экрана истории транзакций.
 */
fun NavGraphBuilder.registerExpensesGraph(
    navController: NavHostController,
    expensesComponent: ExpensesScreenComponent,
    historyComponent: HistoryScreenComponent
) {
    navigation<ExpenseGraph>(startDestination = Expenses) {

        composable<Expenses> {
            val viewModel = viewModel<ExpensesScreenViewModel>(
                factory = expensesComponent.viewModelFactory()
                    .crate()
            )
            ExpensesScreen(
                viewModel = viewModel,
                onNavigateToDetail = {},
                onNavigateToAdded = {},
                onNavigateToHistory = {
                    navController.navigate(ExpensesHistory)
                }
            )
        }

        composable<ExpensesHistory> {
            val viewModel = viewModel<HistoryScreenViewModel>(
                factory = historyComponent.viewModelFactory()
                    .create(TransactionType.Expenses)
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
