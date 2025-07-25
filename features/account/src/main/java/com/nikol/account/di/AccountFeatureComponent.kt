package com.nikol.account.di

import com.nikol.di.FeatureDependencies
import com.nikol.domain.repository.AccountRepository
import com.nikol.domain.repository.TransactionRepository
import dagger.Component

@AccountComponentScope
@Component(
    dependencies = [FeatureDependencies::class],
    modules = [AccountRepositoryModule::class]
)
interface AccountFeatureComponent {

    @Component.Factory
    interface Factory {
        fun create(
            dependencies: FeatureDependencies
        ): AccountFeatureComponent
    }

    fun accountRepository(): AccountRepository

    fun transactionRepository(): TransactionRepository
}
