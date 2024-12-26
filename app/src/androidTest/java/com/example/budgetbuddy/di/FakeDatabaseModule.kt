package com.example.budgetbuddy.di

import android.content.Context
import androidx.room.Room
import com.example.budgetbuddy.database.BudgetBuddyDatabase
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
    fun provideInMemoryDatabase(@ApplicationContext context: Context): BudgetBuddyDatabase {
        return Room.inMemoryDatabaseBuilder(
            context,
            BudgetBuddyDatabase::class.java
        ).allowMainThreadQueries()
            .build()
    }

}
