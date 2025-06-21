package com.nikol.yandexschool.features.articles.screnns.articles.di

import com.nikol.domain.repository.ArticlesRepository
import com.nikol.domain.useCase.GetArticlesUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModuleArticles {

    @ArticlesScreenScope
    @Provides
    fun provideGetArticlesUseCase(articlesRepository: ArticlesRepository): GetArticlesUseCase {
        return GetArticlesUseCase(articlesRepository)
    }
}