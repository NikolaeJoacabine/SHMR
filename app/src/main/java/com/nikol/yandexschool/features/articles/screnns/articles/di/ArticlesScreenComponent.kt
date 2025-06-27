package com.nikol.yandexschool.features.articles.screnns.articles.di

import android.content.Context
import com.nikol.yandexschool.features.articles.di.ArticlesFeatureComponent
import com.nikol.yandexschool.features.articles.screnns.articles.ArticlesScreenViewModelFactoryFactory
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [ArticlesFeatureComponent::class],
    modules = [ArticlesScreenModule::class]
)
@ArticlesScreenScope
interface ArticlesScreenComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            featureComponent: ArticlesFeatureComponent
        ): ArticlesScreenComponent
    }

    fun viewModelFactory(): ArticlesScreenViewModelFactoryFactory
}
