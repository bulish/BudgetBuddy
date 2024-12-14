package com.example.budgetbuddy.ui.screens.transactions.addEdit

import com.example.budgetbuddy.model.db.Place
import com.example.budgetbuddy.model.db.Transaction
import com.example.budgetbuddy.model.db.TransactionCategory
import com.example.budgetbuddy.model.db.TransactionType
import java.time.LocalDateTime

class AddEditTransactionScreenData {
    var transaction: Transaction = Transaction(
        TransactionType.INCOME.value,
        "",
        TransactionCategory.SALARY.value,
        0.0,
        "",
        "",
        "",
        LocalDateTime.now(),
        null
    )

    var transactionReceiptError: Int? = null
    var transactionPriceError: Int? = null
    var transactionNoteError: Int? = null
}

data class CombinedTransactionData(
    val transaction: Transaction?,
    val places: List<Place>,
    val currencies: Map<String, String>
)

