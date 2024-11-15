package com.example.budgetbuddy.ui.screens.settings

import com.google.firebase.auth.FirebaseUser

sealed class SettingsUIState {
    object Loading : SettingsUIState()

    object UserNotAuthorized : SettingsUIState()

    class Success(
        val user: FirebaseUser?
    ) : SettingsUIState()
}
