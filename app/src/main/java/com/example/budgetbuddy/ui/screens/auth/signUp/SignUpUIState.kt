package com.example.budgetbuddy.ui.screens.auth.signUp

import com.example.budgetbuddy.services.UserData
import com.example.budgetbuddy.ui.screens.auth.login.LoginUIState

sealed class SignUpUIState {
    object Default : SignUpUIState()
    class UserSaved(val message: Int) : SignUpUIState()
    class UserChanged(val data: SignUpData) : SignUpUIState()
    class Error(val message: Int) : SignUpUIState()
    class UserLoggedIn(val data: UserData?, val message: Int?) : SignUpUIState()
}
