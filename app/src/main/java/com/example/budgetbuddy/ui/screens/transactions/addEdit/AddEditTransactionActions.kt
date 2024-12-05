package com.example.budgetbuddy.ui.screens.transactions.addEdit

import com.example.budgetbuddy.model.db.Place
import com.example.budgetbuddy.model.db.TransactionCategory

interface AddEditTransactionActions {
    fun onReceiptChange()

    fun onTransactionPriceChange(price: Double)
    fun onTransactionCategoryChanged(category: TransactionCategory)
    fun onTransactionCurrencyChange(currency: String)
    fun onTransactionNoteChange(note: String)
    fun onTransactionDateChange(date: Long?)
    fun onTransactionPlaceChange(place: Place)

    fun saveTransaction()
    fun loadTransaction(id: Long?)
    fun deleteTransaction()
}
