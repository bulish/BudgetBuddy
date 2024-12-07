package com.example.budgetbuddy.ui.screens.transactions.list

import com.example.budgetbuddy.model.db.Transaction

sealed class TransactionsListUIState {

    object UserNotAuthorized : TransactionsListUIState()
    class DataLoaded(val data: List<Transaction>, val sum: Double) : TransactionsListUIState()
    object Loading: TransactionsListUIState()

}
