package com.example.budgetbuddy.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppContainerModule::class]
)
object FakeAppContainerModule {

    @Provides
    @Singleton
    fun provideContext(context: Context): Context {
        return context
    }

}