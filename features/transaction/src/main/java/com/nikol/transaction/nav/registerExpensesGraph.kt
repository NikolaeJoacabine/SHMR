package com.nikol.transaction.nav

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.nikol.domain.model.TransactionType
import com.nikol.navigation.ExpenseGraph
import com.nikol.transaction.screens.add.AddTransactionScreen
import com.nikol.transaction.screens.add.AddTransactionScreenViewModel
import com.nikol.transaction.screens.add.di.AddTransactionScreenComponent
import com.nikol.transaction.screens.analysis.AnalysisScreen
import com.nikol.transaction.screens.analysis.AnalysisScreenViewModel
import com.nikol.transaction.screens.analysis.di.AnalysisScreenComponent
import com.nikol.transaction.screens.edit.EditTransactionScreen
import com.nikol.transaction.screens.edit.EditTransactionScreenViewModel
import com.nikol.transaction.screens.edit.di.EditTransactionScreenComponent
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
 * @param addComponent Dagger-компонент, предоставляющий зависимости для экрана добавления транзакций
 */
internal fun NavGraphBuilder.registerExpensesGraph(
    navController: NavHostController,
    expensesComponent: ExpensesScreenComponent,
    historyComponent: HistoryScreenComponent,
    addComponent: AddTransactionScreenComponent,
    editComponent: EditTransactionScreenComponent,
    analysisComponent: AnalysisScreenComponent
) {
    navigation<ExpenseGraph>(startDestination = Expenses) {

        composable<Expenses> {
            val viewModel = viewModel<ExpensesScreenViewModel>(
                factory = expensesComponent.viewModelFactory()
                    .crate()
            )
            ExpensesScreen(
                viewModel = viewModel,
                onNavigateToDetail = {
                    navController.navigate(ExpensesEdit(it))
                },
                onNavigateToAdded = {
                    navController.navigate(ExpensesAdd)
                },
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
                onNavigateAnalyticScreen = { navController.navigate(ExpensesAnalysis) },
                onNavigateBack = { navController.popBackStack() },
                onNavigateToEditTransactionScreen = {
                    navController.navigate(ExpensesEdit(it))
                }
            )
        }

        composable<ExpensesAdd> {
            val viewModel = viewModel<AddTransactionScreenViewModel>(
                factory = addComponent.viewModelFactory().create(TransactionType.Expenses)
            )
            AddTransactionScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable<ExpensesEdit> {
            val id = it.toRoute<ExpensesEdit>().id
            val viewModel = viewModel<EditTransactionScreenViewModel>(
                factory = editComponent.viewModelFactory().create(id, TransactionType.Expenses)
            )
            EditTransactionScreen(
                viewModel = viewModel,
                navigateBack = { navController.popBackStack() }
            )
        }

        composable<ExpensesAnalysis>{
            val viewModel = viewModel<AnalysisScreenViewModel>(
                factory = analysisComponent.viewModelFactory().create(TransactionType.Expenses)
            )
            AnalysisScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
