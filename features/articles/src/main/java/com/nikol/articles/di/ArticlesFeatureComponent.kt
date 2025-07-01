package com.nikol.articles.di

import com.nikol.di.FeatureDependencies
import com.nikol.domain.repository.ArticlesRepository
import dagger.Component

@Component(
    dependencies = [FeatureDependencies::class],
    modules = [ArticlesRepositoryModule::class]
)
@ArticlesComponentScope
interface ArticlesFeatureComponent {

    @Component.Factory
    interface Factory {
        fun create(
            dependencies: FeatureDependencies
        ): ArticlesFeatureComponent
    }

    fun articlesRepository(): ArticlesRepository
}
