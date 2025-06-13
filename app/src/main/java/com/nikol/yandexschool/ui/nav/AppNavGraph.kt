package com.nikol.yandexschool.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nikol.yandexschool.domain.model.TransactionType
import com.nikol.yandexschool.ui.screens.account.AccountScreen
import com.nikol.yandexschool.ui.screens.amount.AmountScreen
import com.nikol.yandexschool.ui.screens.settings.SettingsScreen
import com.nikol.yandexschool.ui.screens.SplashScreen
import com.nikol.yandexschool.ui.screens.transaction.TransactionScreen

@Composable
fun FinancialDetectiveNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Splash,
        modifier = modifier
    ) {
        composable<Splash> {
            SplashScreen {
                navController.navigate(Expenses) {
                    popUpTo(Splash) { inclusive = true }
                }
            }
        }
        composable<Expenses> { TransactionScreen(transactionType = TransactionType.Expenses) }
        composable<Income> { TransactionScreen(transactionType = TransactionType.Income) }
        composable<Account> { AccountScreen() }
        composable<Articles> { AmountScreen() }
        composable<Settings> { SettingsScreen() }
    }
}