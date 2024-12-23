package com.example.budgetbuddy.ui.screens.transactions.list

import com.example.budgetbuddy.model.db.Transaction
import com.example.budgetbuddy.model.db.TransactionCategory

interface TransactionListScreenActions {

    fun getAllTransactions(category: TransactionCategory?)
    fun transformTransactionPrice(transaction: Transaction): String

}
