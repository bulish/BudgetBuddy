package com.example.budgetbuddy.ui.screens.transactions.detail

import com.example.budgetbuddy.model.db.Transaction

interface DetailTransactionAction {
    fun loadTransaction(id: Long)
    fun deleteTransaction(id: Long?)
}

