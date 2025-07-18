package com.nikol.yandexschool.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nikol.navigation.ExpenseGraph
import com.nikol.navigation.FeatureApi
import com.nikol.yandexschool.ui.splash.SplashScreen
import kotlinx.serialization.Serializable

@Composable
fun FinancialDetectiveNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    list: List<FeatureApi>
) {
    NavHost(
        navController = navController,
        startDestination = ExpenseGraph,
        modifier = modifier
    ) {
        list.forEach {
            it.registerGraph(this, navController)
        }
    }
}
