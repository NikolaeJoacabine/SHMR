package com.nikol.transaction.nav

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.nikol.di.FeatureDependencies
import com.nikol.navigation.FeatureApi
import com.nikol.transaction.di.DaggerTransactionFeatureComponent
import com.nikol.transaction.screens.add.di.DaggerAddTransactionScreenComponent
import com.nikol.transaction.screens.edit.di.DaggerEditTransactionScreenComponent
import com.nikol.transaction.screens.expenses.di.DaggerExpensesScreenComponent
import com.nikol.transaction.screens.history.di.DaggerHistoryScreenComponent
import com.nikol.transaction.screens.income.di.DaggerIncomeScreenComponent

class TransactionFeatureImpl(
    private val dependencies: FeatureDependencies
) : FeatureApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        val featureComponent = DaggerTransactionFeatureComponent.factory().create(dependencies)

        val expensesComponent = DaggerExpensesScreenComponent.factory().create(featureComponent)
        val historyComponent = DaggerHistoryScreenComponent.factory().create(featureComponent)
        val incomeComponent = DaggerIncomeScreenComponent.factory().create(featureComponent)
        val addComponent = DaggerAddTransactionScreenComponent.factory().create(featureComponent)
        val editComponent = DaggerEditTransactionScreenComponent.factory().create(featureComponent)

        navGraphBuilder.registerExpensesGraph(
            navController = navController,
            expensesComponent = expensesComponent,
            historyComponent = historyComponent,
            addComponent = addComponent,
            editComponent = editComponent,
        )
        navGraphBuilder.registerIncomeGraph(
            navController = navController,
            incomeComponent = incomeComponent,
            historyComponent = historyComponent,
            addComponent = addComponent,
            editComponent = editComponent,
        )
    }
}
