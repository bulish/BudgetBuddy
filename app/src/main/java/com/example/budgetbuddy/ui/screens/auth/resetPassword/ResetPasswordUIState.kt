package com.example.budgetbuddy.ui.screens.auth.resetPassword

sealed class ResetPasswordUIState {
    object Default : ResetPasswordUIState()
    object EmailSent : ResetPasswordUIState()
    class EmailChanged(val data: ResetPasswordData) : ResetPasswordUIState()
}
