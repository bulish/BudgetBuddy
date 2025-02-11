package com.example.budgetbuddy.di

import android.content.Context
import com.example.budgetbuddy.services.datastore.DataStoreRepositoryImpl
import com.example.budgetbuddy.services.datastore.IDataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Singleton
    @Provides
    fun provideDataStoreRepository(
        @ApplicationContext appContext: Context
    ): IDataStoreRepository {
        return DataStoreRepositoryImpl(appContext)
    }

}
