package com.example.budgetbuddy.ui.screens.auth.signUp

sealed class SignUpUIState {
    object Default : SignUpUIState()
    object UserSaved : SignUpUIState()
    class UserChanged(val data: SignUpData) : SignUpUIState()
}
