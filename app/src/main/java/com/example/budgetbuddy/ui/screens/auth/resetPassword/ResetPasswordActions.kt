package com.example.budgetbuddy.ui.screens.auth.resetPassword

interface ResetPasswordActions {
    fun onEmailChanged(email: String)
    fun sendEmail()
}
