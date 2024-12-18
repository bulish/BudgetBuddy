package com.example.budgetbuddy.di

import com.example.budgetbuddy.communication.IExchangeRateRemoteRepository
import com.example.budgetbuddy.di.api.RemoteRepositoryModule
import com.example.budgetbuddy.fake.FakeExchangeRateRemoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RemoteRepositoryModule::class],
)
abstract class FakeRemoteRepositoryModule {
    @Binds
    abstract fun provideRemoteRepository(service: FakeExchangeRateRemoteRepositoryImpl): IExchangeRateRemoteRepository
}
