package com.nikol.transaction.nav

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.nikol.domain.model.TransactionType
import com.nikol.navigation.IncomeGraph
import com.nikol.transaction.screens.add.AddTransactionScreen
import com.nikol.transaction.screens.add.AddTransactionScreenViewModel
import com.nikol.transaction.screens.add.di.AddTransactionScreenComponent
import com.nikol.transaction.screens.analysis.AnalysisScreen
import com.nikol.transaction.screens.analysis.AnalysisScreenViewModel
import com.nikol.transaction.screens.analysis.di.AnalysisScreenComponent
import com.nikol.transaction.screens.edit.EditTransactionScreen
import com.nikol.transaction.screens.edit.EditTransactionScreenViewModel
import com.nikol.transaction.screens.edit.di.EditTransactionScreenComponent
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
internal fun NavGraphBuilder.registerIncomeGraph(
    navController: NavHostController,
    incomeComponent: IncomeScreenComponent,
    historyComponent: HistoryScreenComponent,
    addComponent: AddTransactionScreenComponent,
    editComponent: EditTransactionScreenComponent,
    analysisComponent: AnalysisScreenComponent
) {
    navigation<IncomeGraph>(startDestination = Income) {

        composable<Income> {
            val viewModel = viewModel<IncomeScreenViewModel>(
                factory = incomeComponent.viewModelFactory().create()
            )
            IncomeScreen(
                viewModel = viewModel,
                onNavigateToDetail = { navController.navigate(IncomeEdit(it)) },
                onNavigateToAdded = { navController.navigate(IncomeAdd) },
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
                onNavigateAnalyticScreen = { navController.navigate(IncomeAnalysis) },
                onNavigateBack = { navController.popBackStack() },
                onNavigateToEditTransactionScreen = {
                    navController.navigate(IncomeEdit(it))
                }
            )
        }

        composable<IncomeAdd> {
            val viewModel = viewModel<AddTransactionScreenViewModel>(
                factory = addComponent.viewModelFactory().create(TransactionType.Income)
            )
            AddTransactionScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable<IncomeEdit> {
            val id = it.toRoute<IncomeEdit>().id
            val viewModel = viewModel<EditTransactionScreenViewModel>(
                factory = editComponent.viewModelFactory().create(id, TransactionType.Income)
            )
            EditTransactionScreen(
                viewModel = viewModel,
                navigateBack = { navController.popBackStack() }
            )
        }

        composable<IncomeAnalysis> {
            val viewModel = viewModel<AnalysisScreenViewModel>(
                factory = analysisComponent.viewModelFactory().create(TransactionType.Income)
            )
            AnalysisScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
