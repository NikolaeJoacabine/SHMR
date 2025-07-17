package com.nikol.articles.di

import com.nikol.data.articles.ArticlesRepositoryImpl
import com.nikol.data.articles.local.LocalArticlesRepository
import com.nikol.data.articles.local.LocalArticlesRepositoryImpl
import com.nikol.data.articles.local.database.ArticlesDao
import com.nikol.data.articles.remote.RemoteArticlesRepository
import com.nikol.data.articles.remote.RemoteArticlesRepositoryImpl
import com.nikol.data.database.AppDatabase
import com.nikol.data.network.FinanceAPI
import com.nikol.data.network.NetworkStatusProvider
import com.nikol.domain.repository.ArticlesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface ArticlesRepositoryModule {

    @ArticlesComponentScope
    @Binds
    fun bindRemoteArticlesRepositoryModule(
        remoteArticlesRepositoryImpl: RemoteArticlesRepositoryImpl
    ): RemoteArticlesRepository

    @ArticlesComponentScope
    @Binds
    fun bindLocalArticlesRepository(
        localArticlesRepositoryImpl: LocalArticlesRepositoryImpl
    ): LocalArticlesRepository

    @ArticlesComponentScope
    @Binds
    fun bindArticlesRepository(
        articlesRepositoryImpl: ArticlesRepositoryImpl
    ): ArticlesRepository
}
