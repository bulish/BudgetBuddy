package com.example.budgetbuddy.ui.screens.transactions.detail

import com.example.budgetbuddy.ui.elements.shared.labeledelement.LabeledElementData

sealed class DetailTransactionUIState {
    object Loading : DetailTransactionUIState()

    object UserNotAuthorized : DetailTransactionUIState()

    object TransactionDeleted: DetailTransactionUIState()

    class Success(
        val data: List<LabeledElementData>,
    ) : DetailTransactionUIState()
}
