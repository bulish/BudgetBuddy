package com.example.budgetbuddy.ui.screens.home

import com.example.budgetbuddy.model.db.Transaction

sealed class HomeScreenUIState {
    object Loading : HomeScreenUIState()

    object UserNotAuthorized : HomeScreenUIState()

    class Success(
        val transactions: List<Transaction>,
        val totalSum: Double
    ) : HomeScreenUIState()

    class Error(val error: HomeScreenError) : HomeScreenUIState()

    class CurrencyLoaded(
        val data: Map<String, Double>?
    ): HomeScreenUIState()
}
