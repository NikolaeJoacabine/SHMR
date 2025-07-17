package com.nikol.yandexschool.di

import com.nikol.data.di.DatabaseModule
import com.nikol.data.di.LocalDateModule
import com.nikol.data.di.NetworkModule
import dagger.Module

@Module(
    includes = [
        NetworkModule::class,
        LocalDateModule::class,
        DatabaseModule::class,
        SyncModule::class
    ]
)
class AppModule
