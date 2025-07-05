package com.nikol.account.screens.edit.di

import com.nikol.account.di.AccountFeatureComponent
import com.nikol.account.screens.edit.EditScreenViewModel
import dagger.Component


@Component(
    dependencies = [AccountFeatureComponent::class],
    modules = [UseCaseModule::class]
)
@EditScreenScope
internal interface EditScreenComponent {

    @Component.Factory
    interface Factory {
        fun create(
            accountFeatureComponent: AccountFeatureComponent
        ): EditScreenComponent
    }

    fun viewModelFactory(): EditScreenViewModel.Factory.Factory
}
