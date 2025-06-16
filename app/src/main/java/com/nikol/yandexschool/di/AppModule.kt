package com.nikol.yandexschool.di

import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule {
    @Provides
    @Singleton
    fun provideSomeGlobalDependency(): SomeGlobalDep = SomeGlobalDep()
}

class SomeGlobalDep