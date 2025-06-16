package com.nikol.yandexschool.features.articles.nav

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.nikol.yandexschool.di.appComponent
import com.nikol.yandexschool.features.FeatureApi
import com.nikol.yandexschool.features.articles.di.DaggerArticlesFeatureComponent
import com.nikol.yandexschool.ui.nav.Articles
import com.nikol.yandexschool.features.articles.screnns.articles.ArticlesScreen
import com.nikol.yandexschool.features.articles.screnns.articles.di.DaggerArticlesScreenComponent

class ArticlesFeatureApi: FeatureApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {

        val context = navController.context
        val appComponent = context.appComponent
        val featureComponent = DaggerArticlesFeatureComponent.factory()
            .create(context, appComponent)

        val screenComponent = DaggerArticlesScreenComponent.factory()
            .create(context, featureComponent)
        navGraphBuilder.composable<Articles> {
            ArticlesScreen()
        }
    }
}