package com.example.budgetbuddy.ui.screens.transactions.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetbuddy.R
import com.example.budgetbuddy.database.places.ILocalPlacesRepository
import com.example.budgetbuddy.database.transactions.ILocalTransactionsRepository
import com.example.budgetbuddy.extensions.formatToDisplayString
import com.example.budgetbuddy.extensions.toFormattedString
import com.example.budgetbuddy.model.db.Place
import com.example.budgetbuddy.model.db.Transaction
import com.example.budgetbuddy.model.db.TransactionCategory
import com.example.budgetbuddy.model.db.TransactionType
import com.example.budgetbuddy.services.AuthService
import com.example.budgetbuddy.services.IAuthService
import com.example.budgetbuddy.services.datastore.IDataStoreRepository
import com.example.budgetbuddy.ui.elements.shared.labeledelement.LabeledElementData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailTransactionViewModel @Inject constructor(
    private val repository: ILocalTransactionsRepository,
    private val authService: IAuthService,
    private val dataStoreRepository: IDataStoreRepository,
    private val placesRepository: ILocalPlacesRepository
) : ViewModel(), DetailTransactionAction {

    private val _detailUIState: MutableStateFlow<DetailTransactionUIState> =
        MutableStateFlow(value = DetailTransactionUIState.Loading)

    val detailUIState = _detailUIState.asStateFlow()

    private val transactionData: MutableStateFlow<Transaction?> = MutableStateFlow(null)
    private val place: MutableStateFlow<Place?> = MutableStateFlow(null)

    init {
        if (authService.getCurrentUser() == null) {
            _detailUIState.update {
                DetailTransactionUIState.UserNotAuthorized
            }
        }
    }

    override fun loadTransaction(id: Long) {
        val userID = authService.getUserID()

        if (userID != null) {
            viewModelScope.launch {
                val transaction = repository.getTransaction(id, userID).firstOrNull()
                if (transaction != null) {
                    transactionData.value = transaction

                    val placeName = if (transaction.placeId != null) {
                        placesRepository.getPlace(userId = userID, id = transaction.placeId!!)
                            .firstOrNull()?.name ?: "-"
                    } else {
                        "-"
                    }
                    val transactionCategory = MutableStateFlow<TransactionCategory?>(null)

                    val category = TransactionCategory.valueOf(transaction.category.uppercase())
                    transactionCategory.value = category

                    val modifierData = listOf(
                        LabeledElementData(
                            label = R.string.category_placeholder,
                            data = category.name.lowercase(),
                        ),
                        LabeledElementData(
                            label = R.string.price_label,
                            data = "${if (transaction.type == TransactionType.EXPENSE.value) "- " else ""}${transaction.price.toFormattedString()} ${transaction.currency}",
                        ),
                        LabeledElementData(
                            label = R.string.transaction_type_label,
                            data = TransactionType.valueOf(transaction.type.uppercase()).name.lowercase() ?: "-",
                        ),
                        LabeledElementData(
                            label = R.string.date_label,
                            data = transaction.date.formatToDisplayString(),
                        ),
                        LabeledElementData(
                            label = R.string.note_label,
                            data = if (transaction.note != "") transaction.note ?: "-" else "-",
                        ),
                        LabeledElementData(
                            label = R.string.place_label,
                            data = placeName,
                        )
                    )

                    _detailUIState.update {
                        DetailTransactionUIState.Success(modifierData)
                    }
                }
            }
        }
    }

    override fun deleteTransaction(id: Long?) {
       if (id != null && transactionData.value != null) {
           viewModelScope.launch {
               repository.delete(transactionData.value!!)
           }

           _detailUIState.update {
               DetailTransactionUIState.TransactionDeleted(R.string.delete_success)
           }
       }
    }
}
