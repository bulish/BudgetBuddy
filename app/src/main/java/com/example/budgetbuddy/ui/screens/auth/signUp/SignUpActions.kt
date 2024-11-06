package com.example.budgetbuddy.ui.screens.auth.signUp

interface SignUpActions {
    fun onUserEmailChanged(email: String)
    fun onUserPasswordChanged(password: String)
    fun onUserPasswordAgainChanged(password: String)
    fun onUserUsernameChanged(username: String)
    fun saveUser()
}
