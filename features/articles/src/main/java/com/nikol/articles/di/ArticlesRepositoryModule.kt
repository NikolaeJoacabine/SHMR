package com.nikol.articles.di

import com.nikol.data.articles.ArticlesRepositoryImpl
import com.nikol.data.articles.remote.RemoteArticlesRepository
import com.nikol.data.articles.remote.RemoteArticlesRepositoryImpl
import com.nikol.data.network.FinanceAPI
import com.nikol.data.network.NetworkStatusProvider
import com.nikol.domain.repository.ArticlesRepository
import dagger.Module
import dagger.Provides

@Module
class ArticlesRepositoryModule {

    @ArticlesComponentScope
    @Provides
    fun provideRemoteArticlesRepositoryModule(
        financeAPI: FinanceAPI,
        statusProvider: NetworkStatusProvider
    ): RemoteArticlesRepository {
        return RemoteArticlesRepositoryImpl(financeAPI, statusProvider)
    }

    @ArticlesComponentScope
    @Provides
    fun provideArticlesRepository(
        remoteArticlesRepository: RemoteArticlesRepository
    ): ArticlesRepository {
        return ArticlesRepositoryImpl(remoteArticlesRepository)
    }
}
