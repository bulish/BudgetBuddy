package com.example.budgetbuddy.fake

import com.example.budgetbuddy.model.FakeUserData
import com.example.budgetbuddy.services.IAuthService
import com.example.budgetbuddy.services.UserData
import com.google.firebase.auth.FirebaseAuth
import io.mockk.every
import javax.inject.Inject

class FakeAuthService @Inject constructor(
    private val auth: FirebaseAuth
) : IAuthService {

    var user = FakeUserData(
        uid = "123",
        displayName = "Test user",
        email = "test@test.cz"
    )

    override fun getCurrentUser(): UserData {
        return UserData.Fake(user)
    }

    override fun getUsername(): String? {
        every { user.displayName } returns "Fake User"
        return user.displayName
    }

    override fun getUserID(): String? {
        every { user.uid } returns "123"
        return user.uid
    }

    override fun signOut() {
        auth.signOut()
    }
}
