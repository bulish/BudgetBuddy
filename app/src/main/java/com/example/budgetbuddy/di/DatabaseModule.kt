package com.example.budgetbuddy.di

import android.content.Context
import com.example.budgetbuddy.database.BudgetBuddyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideBudgetBuddyDatabase(@ApplicationContext context: Context): BudgetBuddyDatabase {
        return BudgetBuddyDatabase.getDatabase(context)
    }

}
