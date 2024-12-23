package com.example.budgetbuddy.services

import com.example.budgetbuddy.model.FakeUserData
import com.google.firebase.auth.FirebaseUser

sealed class UserData {
    abstract val displayName: String?
    abstract val email: String?

    data class Firebase(val user: FirebaseUser?) : UserData() {
        override val displayName: String?
            get() = user?.displayName

        override val email: String?
            get() = user?.email
    }

    data class Fake(val data: FakeUserData) : UserData() {
        override val displayName: String?
            get() = data.displayName

        override val email: String?
            get() = data.email
    }
}

interface IAuthService {

    fun getCurrentUser(): UserData
    fun getUsername(): String?
    fun getUserID() : String?
    fun signOut()

}
