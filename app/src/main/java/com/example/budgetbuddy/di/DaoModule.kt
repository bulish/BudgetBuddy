package com.example.budgetbuddy.di

import com.example.budgetbuddy.database.BudgetBuddyDatabase
import com.example.budgetbuddy.database.places.PlacesDao
import com.example.budgetbuddy.database.transactions.TransactionsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun providePlaces(database: BudgetBuddyDatabase): PlacesDao {
        return database.placesDao()
    }

    @Provides
    @Singleton
    fun provideTransactions(database: BudgetBuddyDatabase): TransactionsDao {
        return database.transactionsDao()
    }

}
