package com.example.budgetbuddy.di.api

import com.example.budgetbuddy.communication.CurrencyAPI
import com.example.budgetbuddy.communication.ExchangeRateRemoteRepositoryImpl
import com.example.budgetbuddy.communication.IExchangeRateRemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteRepositoryModule {

    @Provides
    @Singleton
    fun provideCurrencyRepository(currencyAPI: CurrencyAPI) : IExchangeRateRemoteRepository {
        return ExchangeRateRemoteRepositoryImpl(currencyAPI)
    }

}
