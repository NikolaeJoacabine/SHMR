package com.nikol.yandexschool.features.account.screens.account.di

import android.content.Context
import com.nikol.yandexschool.features.account.di.AccountFeatureComponent
import com.nikol.yandexschool.features.account.screens.account.AccountScreenViewModelFactoryFactory
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [AccountFeatureComponent::class],
    modules = [AccountScreenModule::class]
)
@AccountScreenScope
interface AccountScreenComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            featureComponent: AccountFeatureComponent
        ): AccountScreenComponent
    }

    fun viewModelFactoryFactory(): AccountScreenViewModelFactoryFactory
}
