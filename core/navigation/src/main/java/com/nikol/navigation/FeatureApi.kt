package com.nikol.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

/**
 * Интерфейс для регистрации навигационных графов в приложении.
 *
 * Каждый feature реализует этот интерфейс, чтобы описать
 * свой навигационный граф, добавляя необходимые маршруты и экраны.
 */
interface FeatureApi {

    /**
     * Регистрирует навигационный граф для конкретного feature.
     *
     * @param navGraphBuilder билдер навигационного графа, куда добавляются экраны.
     * @param navController контроллер навигации для управления переходами.
     * @param modifier опциональный параметр для модификации UI компонентов.
     */
    fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier = Modifier
    )
}
