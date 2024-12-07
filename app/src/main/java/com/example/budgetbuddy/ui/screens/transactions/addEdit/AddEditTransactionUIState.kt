package com.example.budgetbuddy.ui.screens.transactions.addEdit

import com.example.budgetbuddy.model.db.Place
import com.example.budgetbuddy.ui.screens.places.map.MapScreenUIState

sealed class AddEditTransactionUIState {
    object Loading : AddEditTransactionUIState()
    object TransactionSaved : AddEditTransactionUIState()
    object UserNotAuthorized : AddEditTransactionUIState()
    class TransactionChanged(val data: AddEditTransactionScreenData) : AddEditTransactionUIState()
    object TransactionDeleted : AddEditTransactionUIState()
    class PlacesLoaded(val data: List<Place>) : AddEditTransactionUIState()
}
