package com.nikol.yandexschool.features.transaction.di

import android.content.Context
import com.nikol.yandexschool.di.AppComponent
import com.nikol.yandexschool.features.transaction.screens.transaction.di.TransactionScreenComponent
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [TransactionFeatureModule::class]
)
@TransactionComponentScope
interface TransactionFeatureComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            appComponent: AppComponent
        ): TransactionFeatureComponent
    }
}