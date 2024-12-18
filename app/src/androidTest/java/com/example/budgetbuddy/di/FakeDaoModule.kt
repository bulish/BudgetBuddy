package com.example.budgetbuddy.di

import com.example.budgetbuddy.database.places.PlacesDao
import com.example.budgetbuddy.database.transactions.TransactionsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.every
import javax.inject.Singleton
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DaoModule::class]
)
object FakeDaoModule {

    @Provides
    @Singleton
    fun provideTransactions(): TransactionsDao {
        val transactionsDao = mockk<TransactionsDao>()

        every { transactionsDao.getAllByUser(any()) } returns flowOf(listOf())

        return transactionsDao
    }

    @Provides
    @Singleton
    fun providePlaces(): PlacesDao {
        return mockk()
    }
}
