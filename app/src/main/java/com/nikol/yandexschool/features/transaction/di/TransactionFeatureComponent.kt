package com.nikol.yandexschool.features.transaction.di

import android.content.Context
import com.nikol.domain.common.TimeProvider
import com.nikol.domain.repository.AccountRepository
import com.nikol.domain.repository.TransactionRepository
import com.nikol.yandexschool.di.AppComponent
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

    fun transactionRepository(): TransactionRepository
    fun accountRepository(): AccountRepository

    fun timeProvider(): TimeProvider

}
