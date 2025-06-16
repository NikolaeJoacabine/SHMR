package com.nikol.yandexschool.features.articles.screnns.articles.di

import com.nikol.domain.useCase.GetArticlesUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModuleArticles {

    @ArticlesScreenScope
    @Provides
    fun provideGetArticlesUseCase(): GetArticlesUseCase {
        return GetArticlesUseCase()
    }
}