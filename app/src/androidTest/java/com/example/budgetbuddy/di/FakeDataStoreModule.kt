package com.example.budgetbuddy.di

import android.content.Context
import com.example.budgetbuddy.fake.FakeDataStoreRepositoryImpl
import com.example.budgetbuddy.services.datastore.IDataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataStoreModule::class]
)
object FakeDataStoreModule {

    @Singleton
    @Provides
    fun provideFakeDataStoreRepository(
        @ApplicationContext appContext: Context
    ): IDataStoreRepository {
        return FakeDataStoreRepositoryImpl(appContext)
    }

}