package com.example.budgetbuddy.ui.screens.transactions.addEdit

sealed class AddEditTransactionUIState {
    object Loading : AddEditTransactionUIState()
    object TransactionSaved : AddEditTransactionUIState()
    object UserNotAuthorized : AddEditTransactionUIState()
    class TransactionChanged(val data: AddEditTransactionScreenData) : AddEditTransactionUIState()
    object TransactionDeleted : AddEditTransactionUIState()
}
