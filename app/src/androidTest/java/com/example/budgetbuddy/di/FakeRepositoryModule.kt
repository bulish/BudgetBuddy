package com.example.budgetbuddy.di

import com.example.budgetbuddy.database.places.ILocalPlacesRepository
import com.example.budgetbuddy.database.places.LocalPlacesRepositoryImpl
import com.example.budgetbuddy.database.transactions.ILocalTransactionsRepository
import com.example.budgetbuddy.database.transactions.LocalTransactionsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class],
)
abstract class FakeRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPlacesRepository(
        repository: LocalPlacesRepositoryImpl
    ): ILocalPlacesRepository

    @Binds
    @Singleton
    abstract fun bindTransactionsRepository(
        repository: LocalTransactionsRepositoryImpl
    ): ILocalTransactionsRepository

}