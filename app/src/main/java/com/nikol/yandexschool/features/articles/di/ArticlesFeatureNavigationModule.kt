package com.nikol.yandexschool.features.articles.di

import com.nikol.yandexschool.di.NavigationScope
import com.nikol.yandexschool.features.FeatureApi
import com.nikol.yandexschool.features.articles.nav.ArticlesFeatureApi
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet


@Module
class ArticlesFeatureNavigationModule {

    @NavigationScope
    @Provides
    @IntoSet
    fun provideArticlesFeatureApi(): FeatureApi {
        return ArticlesFeatureApi()
    }
}