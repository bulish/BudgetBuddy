package com.example.budgetbuddy.ui.screens.auth.login

import com.google.firebase.auth.FirebaseUser

sealed class LoginUIState {
    object Default : LoginUIState()
    class UserLoggedIn(val data: FirebaseUser?) : LoginUIState()
    class UserChanged(val data: LoginData) : LoginUIState()
}
