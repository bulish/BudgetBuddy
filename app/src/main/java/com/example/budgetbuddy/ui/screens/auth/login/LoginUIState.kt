package com.example.budgetbuddy.ui.screens.auth.login

import com.example.budgetbuddy.services.UserData

sealed class LoginUIState {
    object Default : LoginUIState()
    class UserLoggedIn(val data: UserData?, val message: Int?) : LoginUIState()
    class UserChanged(val data: LoginData) : LoginUIState()
    class Error(val message: Int) : LoginUIState()
}
