package com.example.budgetbuddy.di

import android.content.Context
import com.example.budgetbuddy.database.BudgetBuddyDatabase
import com.example.budgetbuddy.database.places.PlacesDao
import com.example.budgetbuddy.database.transactions.TransactionsDao
import com.example.budgetbuddy.fake.FakeBudgetBuddyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
object FakeDatabaseModule {

    @Provides
    @Singleton
    fun provideFakeBudgetBuddyDatabase(context: Context): BudgetBuddyDatabase {
        return FakeBudgetBuddyDatabase.getInMemoryDatabase(context)
    }

}
