package com.example.budgetbuddy.fake

import com.example.budgetbuddy.model.FakeUserData
import com.example.budgetbuddy.services.IAuthService
import com.example.budgetbuddy.services.UserData
import com.google.firebase.auth.FirebaseAuth
import io.mockk.every
import io.mockk.mockk
import javax.inject.Inject

class FakeAuthService @Inject constructor(
    private val auth: FirebaseAuth
) : IAuthService {

    private val mockUser: FakeUserData = mockk()

    init {
        every { mockUser.uid } returns "123"
        every { mockUser.displayName } returns "Test user"
        every { mockUser.email } returns "test@test.cz"
    }

    override fun getCurrentUser(): UserData {
        return UserData.Fake(mockUser)
    }

    override fun getUsername(): String? {
        return mockUser.displayName
    }

    override fun getUserID(): String? {
        return mockUser.uid
    }

    override fun signOut() {
        auth.signOut()
    }
}
