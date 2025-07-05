package com.nikol.account.screens.account.di

import com.nikol.account.di.AccountFeatureComponent
import com.nikol.account.screens.account.AccountScreenViewModel
import dagger.Component

@AccountScreenScope
@Component(
    dependencies = [AccountFeatureComponent::class],
    modules = [UseCaseModule::class]
)
internal interface AccountScreenComponent {

    @Component.Factory
    interface Factory {
        fun create(
            accountFeatureComponent: AccountFeatureComponent
        ): AccountScreenComponent
    }

    fun viewModelFactory(): AccountScreenViewModel.Factory.Factory
}
