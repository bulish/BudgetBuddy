package com.example.budgetbuddy.ui.screens.settings

import com.example.budgetbuddy.model.PrimaryColor

interface SettingsActions {

    fun changeMode()
    fun changePrimaryColor(color: PrimaryColor)
    fun changeCurrency(currency: String)
    fun signOut()
    fun getCurrencyData()

}
