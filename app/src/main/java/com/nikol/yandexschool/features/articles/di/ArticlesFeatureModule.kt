package com.nikol.yandexschool.features.articles.di

import dagger.Module


@Module(includes = [ArticlesRepositoryModule::class])
class ArticlesFeatureModule
