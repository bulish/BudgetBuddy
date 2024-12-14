package com.example.budgetbuddy.ui.screens.transactions.addEdit

import com.example.budgetbuddy.model.db.Place
import com.example.budgetbuddy.ui.screens.places.map.MapScreenUIState
import com.example.budgetbuddy.ui.screens.settings.SettingsScreenError
import com.example.budgetbuddy.ui.screens.settings.SettingsUIState

sealed class AddEditTransactionUIState {
    object Loading : AddEditTransactionUIState()
    class TransactionSaved(val message: Int) : AddEditTransactionUIState()
    object UserNotAuthorized : AddEditTransactionUIState()
    class TransactionChanged(val data: AddEditTransactionScreenData) : AddEditTransactionUIState()
    object TransactionDeleted : AddEditTransactionUIState()
    class Error(val error: AddEditTransactionScreenError) : AddEditTransactionUIState()
    class TransactionDataLoaded(val data: CombinedTransactionData): AddEditTransactionUIState()

}
