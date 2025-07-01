package com.nikol.transaction.di

import com.nikol.di.FeatureDependencies
import com.nikol.domain.common.TimeProvider
import com.nikol.domain.repository.AccountRepository
import com.nikol.domain.repository.TransactionRepository
import dagger.Component

@TransactionComponentScope
@Component(
    dependencies = [FeatureDependencies::class],
    modules = [TransactionRepositoryModule::class]
)
interface TransactionFeatureComponent {

    @Component.Factory
    interface Factory {
        fun create(
            dependencies: FeatureDependencies
        ): TransactionFeatureComponent
    }

    fun transactionRepository(): TransactionRepository
    fun accountRepository(): AccountRepository
    fun timeProvider(): TimeProvider
}
