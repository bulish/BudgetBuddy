package com.example.budgetbuddy.ui.screens.settings

import com.example.budgetbuddy.ui.screens.home.HomeScreenError
import com.example.budgetbuddy.ui.screens.home.HomeScreenUIState
import com.google.firebase.auth.FirebaseUser

sealed class SettingsUIState {
    object Loading : SettingsUIState()

    class UserNotAuthorized(val message: Int?) : SettingsUIState()

    class Success(
        val user: FirebaseUser?
    ) : SettingsUIState()

    class Error(val error: SettingsScreenError) : SettingsUIState()

    class CurrencyLoaded(
        val data: Map<String, Double>?
    ): SettingsUIState()
}
