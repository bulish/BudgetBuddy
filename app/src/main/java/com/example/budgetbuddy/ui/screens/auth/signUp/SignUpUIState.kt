package com.example.budgetbuddy.ui.screens.auth.signUp

sealed class SignUpUIState {
    object Default : SignUpUIState()
    class UserSaved(val message: Int) : SignUpUIState()
    class UserChanged(val data: SignUpData) : SignUpUIState()
    class Error(val message: Int) : SignUpUIState()
}
