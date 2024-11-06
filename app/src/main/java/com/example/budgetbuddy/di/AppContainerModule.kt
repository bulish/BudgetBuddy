package com.example.budgetbuddy.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppContainerModule {

    @Provides
    @Singleton
    fun provideContext(context: Context): Context {
        return context
    }

}
