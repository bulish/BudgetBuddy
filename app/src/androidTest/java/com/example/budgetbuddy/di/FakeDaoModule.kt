package com.example.budgetbuddy.di

import com.example.budgetbuddy.database.places.PlacesDao
import com.example.budgetbuddy.database.transactions.TransactionsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton
import io.mockk.mockk
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DaoModule::class]
)
object FakeDaoModule {

    @Provides
    @Singleton
    fun provideTransactions(): TransactionsDao {
        return mockk()
    }

    @Provides
    @Singleton
    fun providePlaces(): PlacesDao {
        return mockk()
    }
}
