package com.example.budgetbuddy.di

import com.example.budgetbuddy.database.BudgetBuddyDatabase
import com.example.budgetbuddy.database.places.PlacesDao
import com.example.budgetbuddy.database.transactions.TransactionsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DaoModule::class]
)
object FakeDaoModule {

    @Provides
    @Singleton
    fun providePlacesDao(database: BudgetBuddyDatabase): PlacesDao {
        return database.placesDao()
    }

    @Provides
    @Singleton
    fun provideTransactionsDao(database: BudgetBuddyDatabase): TransactionsDao {
        return database.transactionsDao()
    }
}

