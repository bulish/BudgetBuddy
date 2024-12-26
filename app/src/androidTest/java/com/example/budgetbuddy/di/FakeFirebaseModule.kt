package com.example.budgetbuddy.di

import com.example.budgetbuddy.fake.FakeAuthService
import com.example.budgetbuddy.services.IAuthService
import dagger.hilt.testing.TestInstallIn
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [FireBaseAuth::class]
)
object TestAuthServiceModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuthService(firebaseAuth: FirebaseAuth): IAuthService {
        return FakeAuthService(firebaseAuth)
    }
}
