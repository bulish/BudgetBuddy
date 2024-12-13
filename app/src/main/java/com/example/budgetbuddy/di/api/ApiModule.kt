package com.example.budgetbuddy.di.api

import com.example.budgetbuddy.communication.CurrencyAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideCurrencyAPI(retrofit: Retrofit): CurrencyAPI {
        return retrofit.create(CurrencyAPI::class.java)
    }

}
