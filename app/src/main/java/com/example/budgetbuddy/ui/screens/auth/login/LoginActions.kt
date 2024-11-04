package com.example.budgetbuddy.ui.screens.auth.login

interface LoginActions {
    fun onUserEmailChanged(email: String)
    fun onUserPasswordChanged(password: String)
    fun loginUser()
}
