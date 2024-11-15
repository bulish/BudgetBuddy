package com.example.budgetbuddy.ui.screens.settings

interface SettingsActions {

    fun changeMode()
    fun changeCurrency(currency: String)
    fun signOut()
    fun getUserInformation()

}
