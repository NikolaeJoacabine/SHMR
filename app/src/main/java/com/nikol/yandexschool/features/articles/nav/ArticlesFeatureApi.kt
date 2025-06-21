package com.nikol.yandexschool.features.articles.nav

import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.nikol.yandexschool.di.appComponent
import com.nikol.yandexschool.features.FeatureApi
import com.nikol.yandexschool.features.articles.di.DaggerArticlesFeatureComponent
import com.nikol.yandexschool.ui.nav.Articles
import com.nikol.yandexschool.features.articles.screnns.articles.ArticlesScreen
import com.nikol.yandexschool.features.articles.screnns.articles.ArticlesScreenViewModel
import com.nikol.yandexschool.features.articles.screnns.articles.di.DaggerArticlesScreenComponent

class ArticlesFeatureApi : FeatureApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {

        val context = navController.context
        val appComponent = context.appComponent

        val articlesFeatureComponent = DaggerArticlesFeatureComponent.factory()
            .create(context, appComponent)

        val articlesScreenComponent = DaggerArticlesScreenComponent.factory()
            .create(context, articlesFeatureComponent)

        navGraphBuilder.composable<Articles> {
            val viewModel = viewModel<ArticlesScreenViewModel>(
                factory = articlesScreenComponent.viewModelFactory().create()
            )
            ArticlesScreen(viewModel)
        }
    }
}