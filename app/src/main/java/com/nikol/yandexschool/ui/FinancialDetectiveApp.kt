package com.nikol.yandexschool.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nikol.navigation.AccountGraph
import com.nikol.navigation.ArticleGraph
import com.nikol.navigation.ExpenseGraph
import com.nikol.navigation.FeatureApi
import com.nikol.navigation.IncomeGraph
import com.nikol.navigation.SettingsGraph
import com.nikol.yandexschool.R
import com.nikol.yandexschool.ui.nav.FinancialDetectiveNavGraph
import com.nikol.yandexschool.ui.nav.Splash
import com.nikol.yandexschool.ui.nav.TopLevelRoute

@Composable
fun FinancialDetectiveApp(modifier: Modifier = Modifier, listFeature: List<FeatureApi>) {

    val list = listOf(
        TopLevelRoute(R.string.expenses, ExpenseGraph, R.drawable.downtrend),
        TopLevelRoute(R.string.income, IncomeGraph, R.drawable.uptrend),
        TopLevelRoute(R.string.score, AccountGraph, R.drawable.calculator),
        TopLevelRoute(R.string.articles, ArticleGraph, R.drawable.bar_chart_side),
        TopLevelRoute(R.string.settings, SettingsGraph, R.drawable.settings)
    )

    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry.value?.destination

    val currentRoute = currentDestination?.route

    Scaffold(
        modifier = modifier, bottomBar = {
            if (currentRoute != null && currentRoute != Splash.serializer().descriptor.serialName) {
                NavigationBar {
                    list.forEach { topLevelRoute ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    painter = painterResource(topLevelRoute.icon),
                                    contentDescription = stringResource(topLevelRoute.name)
                                )
                            }, label = {
                                Text(
                                    text = stringResource(topLevelRoute.name),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }, selected = currentDestination.hierarchy.any {
                                it.hasRoute(topLevelRoute.route::class)
                            }, onClick = {
                                navController.navigate(topLevelRoute.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }, colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                            )
                        )
                    }
                }
            }
        }) { innerPadding ->
        FinancialDetectiveNavGraph(
            navController = navController,
            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
            list = listFeature
        )
    }
}
