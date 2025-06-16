package com.nikol.yandexschool.features.articles.screnns.articles.di

import com.nikol.domain.useCase.GetArticlesUseCase
import com.nikol.yandexschool.features.articles.screnns.articles.ArticlesScreenViewModelFactoryFactory
import dagger.Module
import dagger.Provides

@Module(
    includes = [UseCaseModuleArticles::class]
)
class ArticlesScreenModule {
    @ArticlesScreenScope
    @Provides
    fun provideArticlesScreenViewModelFactoryFactory(getArticlesUseCase: GetArticlesUseCase): ArticlesScreenViewModelFactoryFactory {
        return ArticlesScreenViewModelFactoryFactory(getArticlesUseCase)
    }
}