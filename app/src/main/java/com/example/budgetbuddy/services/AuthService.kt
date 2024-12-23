package com.example.budgetbuddy.services

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class AuthService @Inject constructor(
    private val auth: FirebaseAuth
): IAuthService {

    init {
        println("Hello user from auth service")
    }

    override fun getCurrentUser(): UserData {
        return UserData.Firebase(auth.currentUser)
    }

    override fun getUsername(): String? {
        return auth.currentUser?.displayName
    }

    override fun getUserID() : String? {
        return auth.currentUser?.uid
    }

    override fun signOut() {
        auth.signOut()
    }
}

