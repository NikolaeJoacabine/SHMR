package com.nikol.articles.nav

import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.nikol.articles.di.DaggerArticlesFeatureComponent
import com.nikol.articles.screens.articles.ArticlesScreen
import com.nikol.articles.screens.articles.ArticlesScreenViewModel
import com.nikol.articles.screens.articles.di.DaggerArticlesScreenComponent
import com.nikol.di.FeatureDependencies
import com.nikol.navigation.ArticleGraph
import com.nikol.navigation.FeatureApi

class ArticlesFeatureImpl(
    private val dependencies: FeatureDependencies
) : FeatureApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        val featureComponent = DaggerArticlesFeatureComponent.factory().create(dependencies)
        val articlesComponent = DaggerArticlesScreenComponent.factory().create(featureComponent)

        navGraphBuilder.navigation<ArticleGraph>(startDestination = Articles) {

            composable<Articles> {
                val viewModel = viewModel<ArticlesScreenViewModel>(
                    factory = articlesComponent.viewModelFactory().create()
                )
                ArticlesScreen(
                    viewModel = viewModel
                )
            }
        }
    }
}
