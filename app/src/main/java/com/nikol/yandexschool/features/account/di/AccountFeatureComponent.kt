package com.nikol.yandexschool.features.account.di

import android.content.Context
import com.nikol.domain.repository.AccountRepository
import com.nikol.yandexschool.di.AppComponent
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [AccountFeatureModule::class]
)
@AccountComponentScope
interface AccountFeatureComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            appComponent: AppComponent
        ): AccountFeatureComponent
    }

    fun accountRepository(): AccountRepository
}
