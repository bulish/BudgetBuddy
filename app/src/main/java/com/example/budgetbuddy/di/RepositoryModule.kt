package com.example.budgetbuddy.di

import com.example.budgetbuddy.database.places.ILocalPlacesRepository
import com.example.budgetbuddy.database.places.LocalPlacesRepositoryImpl
import com.example.budgetbuddy.database.places.PlacesDao
import com.example.budgetbuddy.database.transactions.ILocalTransactionsRepository
import com.example.budgetbuddy.database.transactions.LocalTransactionsRepositoryImpl
import com.example.budgetbuddy.database.transactions.TransactionsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTransactionsRepository(dao: TransactionsDao): ILocalTransactionsRepository {
        return LocalTransactionsRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun providePlacesRepository(dao: PlacesDao): ILocalPlacesRepository {
        return LocalPlacesRepositoryImpl(dao)
    }

}
