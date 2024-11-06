package com.example.budgetbuddy.services

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthService(private val auth: FirebaseAuth) {

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun getUsername(): String? {
        return getCurrentUser()?.displayName
    }

    fun getUserID() : String? {
        return getCurrentUser()?.uid
    }

    fun signOut() {
        auth.signOut()
    }
}

