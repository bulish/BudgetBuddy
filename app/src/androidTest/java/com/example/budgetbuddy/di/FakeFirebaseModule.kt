package com.example.budgetbuddy.di

import dagger.hilt.testing.TestInstallIn
import com.example.budgetbuddy.services.AuthService
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [FireBaseAuth::class],
)
class FakeFirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuthService(auth: FirebaseAuth): AuthService {
        return AuthService(auth)
    }

}
