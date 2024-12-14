package com.example.budgetbuddy.ui.screens.auth.login

import com.google.firebase.auth.FirebaseUser

sealed class LoginUIState {
    object Default : LoginUIState()
    class UserLoggedIn(val data: FirebaseUser?, val message: Int?) : LoginUIState()
    class UserChanged(val data: LoginData) : LoginUIState()
    class Error(val message: Int) : LoginUIState()
}
