package com.example.budgetbuddy.ui.screens.auth.resetPassword

sealed class ResetPasswordUIState {
    object Default : ResetPasswordUIState()
    class EmailSent(val message: Int) : ResetPasswordUIState()
    class EmailChanged(val data: ResetPasswordData) : ResetPasswordUIState()
    class Error(val message: Int): ResetPasswordUIState()
}
