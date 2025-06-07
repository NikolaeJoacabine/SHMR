package com.nikol.yandexschool

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nikol.yandexschool.ui.screens.ArticlesScreen
import com.nikol.yandexschool.ui.screens.ExpensesScreen
import com.nikol.yandexschool.ui.screens.IncomeScreen
import com.nikol.yandexschool.ui.screens.ScoreScreen
import com.nikol.yandexschool.ui.screens.SettingsScreen

enum class FinancialDetectiveScreens(
    @StringRes val title: Int,
    @DrawableRes val icon: Int
) {
    Expenses(
        title = R.string.expenses,
        icon = R.drawable.downtrend
    ),
    Income(
        title = R.string.income,
        icon = R.drawable.uptrend
    ),
    Score(
        title = R.string.score,
        icon = R.drawable.calculator
    ),
    Articles(
        title = R.string.articles,
        icon = R.drawable.bar_chart_side
    ),
    Settings(
        title = R.string.settings,
        icon = R.drawable.settings
    )
}

@Composable
fun FinancialDetectiveApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()

    val currentDestination = backStackEntry.value?.destination?.route
    val currentScreen = FinancialDetectiveScreens.entries
        .find { it.name == currentDestination }
        ?: FinancialDetectiveScreens.Expenses

    Scaffold(
        modifier = modifier,
        bottomBar = {
            FinancialDetectiveAppBar(
                items = FinancialDetectiveScreens.entries,
                currentScreen = currentScreen,
                onClick = { screen ->
                    if (screen.name != currentDestination) {
                        navController.navigate(screen.name) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        FinancialDetectiveNavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun FinancialDetectiveAppBar(
    items: List<FinancialDetectiveScreens>,
    currentScreen: FinancialDetectiveScreens,
    onClick: (FinancialDetectiveScreens) -> Unit
) {
    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(painterResource(id = screen.icon), contentDescription = null) },
                label = {
                    Text(
                        stringResource(screen.title),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                selected = screen == currentScreen,
                onClick = { onClick(screen) }
            )
        }
    }
}

@Composable
fun FinancialDetectiveNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = FinancialDetectiveScreens.Expenses.name,
        modifier = modifier
    ) {
        composable(FinancialDetectiveScreens.Expenses.name) {
            ExpensesScreen()
        }
        composable(FinancialDetectiveScreens.Income.name) {
            IncomeScreen()
        }
        composable(FinancialDetectiveScreens.Score.name) {
            ScoreScreen()
        }
        composable(FinancialDetectiveScreens.Articles.name) {
            ArticlesScreen()
        }
        composable(FinancialDetectiveScreens.Settings.name) {
            SettingsScreen()
        }
    }
}