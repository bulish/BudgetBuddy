package com.example.budgetbuddy.ui.screens.home

import com.example.budgetbuddy.model.db.Transaction

interface HomeScreenActions {
    fun loadTransactions()
    fun changeCurrency(currency: String)
    fun getCurrencyData()
    fun transformTransactionPrice(transaction: Transaction): String
}
