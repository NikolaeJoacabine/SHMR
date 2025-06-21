package com.nikol.yandexschool.di

import com.nikol.data.network.NetworkModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module(includes = [NetworkModule::class])
class AppModule {
    @Provides
    @Singleton
    fun provideSomeGlobalDependency(): SomeGlobalDep = SomeGlobalDep()
}

class SomeGlobalDep