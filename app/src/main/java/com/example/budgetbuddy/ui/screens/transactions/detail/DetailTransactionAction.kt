package com.example.budgetbuddy.ui.screens.transactions.detail

interface DetailTransactionAction {
    fun loadTransaction(id: Long)
    fun deleteTransaction(id: Long?)
}

