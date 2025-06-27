package com.nikol.yandexschool.features.transaction.nav

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.nikol.domain.model.TransactionType
import com.nikol.yandexschool.features.transaction.screens.expenses.ExpensesScreen
import com.nikol.yandexschool.features.transaction.screens.expenses.ExpensesScreenViewModel
import com.nikol.yandexschool.features.transaction.screens.expenses.di.ExpensesScreenComponent
import com.nikol.yandexschool.features.transaction.screens.history.HistoryScreen
import com.nikol.yandexschool.features.transaction.screens.history.HistoryScreenViewModel
import com.nikol.yandexschool.features.transaction.screens.history.di.HistoryScreenComponent
import com.nikol.yandexschool.ui.nav.Expenses
import com.nikol.yandexschool.ui.nav.ExpensesGraph
import com.nikol.yandexschool.ui.nav.ExpensesHistory

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
    navigation<ExpensesGraph>(startDestination = Expenses) {

        composable<Expenses> {
            val viewModel = viewModel<ExpensesScreenViewModel>(
                factory = expensesComponent.viewModelFactoryFactory().create()
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
                factory = historyComponent.viewModelFactoryFactory()
                    .create(TransactionType.Expenses)
            )
            HistoryScreen(
                viewModel = viewModel,
                onNavigateAnalyticScreen = {},
                onNavigateBack = { navController.popBackStack(Expenses, inclusive = false) },
                onNavigateToEditTransactionScreen = {}
            )
        }
    }
}
