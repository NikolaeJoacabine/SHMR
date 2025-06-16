package com.nikol.yandexschool.features.articles.di

import android.content.Context
import com.nikol.yandexschool.di.AppComponent
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [ArticlesFeatureModule::class]
)
@ArticlesComponentScope
interface ArticlesFeatureComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            appComponent: AppComponent
        ): ArticlesFeatureComponent
    }
}
