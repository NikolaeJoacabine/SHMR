package com.nikol.articles.screens.articles.di

import com.nikol.articles.di.ArticlesFeatureComponent
import com.nikol.articles.screens.articles.ArticlesScreenViewModel
import dagger.Component

@ArticlesScreenScope
@Component(
    dependencies = [ArticlesFeatureComponent::class],
    modules = [UseCaseModule::class]
)
interface ArticlesScreenComponent {

    @Component.Factory
    interface Factory {
        fun create(
            articlesFeatureComponent: ArticlesFeatureComponent
        ): ArticlesScreenComponent
    }

    fun viewModelFactory(): ArticlesScreenViewModel.Factory.Factory
}
