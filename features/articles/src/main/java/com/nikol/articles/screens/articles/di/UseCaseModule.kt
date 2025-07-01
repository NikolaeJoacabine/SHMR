package com.nikol.articles.screens.articles.di

import com.nikol.domain.repository.ArticlesRepository
import com.nikol.domain.useCase.GetArticlesUseCase
import com.nikol.domain.useCase.SearchArticlesUseCase
import dagger.Module
import dagger.Provides

@Module
object UseCaseModule {

    @ArticlesScreenScope
    @Provides
    fun provideGetArticlesUseCase(articlesRepository: ArticlesRepository): GetArticlesUseCase {
        return GetArticlesUseCase(articlesRepository)
    }

    @ArticlesScreenScope
    @Provides
    fun provideSearchArticlesUseCase(): SearchArticlesUseCase {
        return SearchArticlesUseCase()
    }
}
