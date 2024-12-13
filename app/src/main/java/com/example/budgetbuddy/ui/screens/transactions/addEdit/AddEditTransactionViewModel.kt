package com.example.budgetbuddy.ui.screens.transactions.addEdit

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetbuddy.R
import com.example.budgetbuddy.database.places.ILocalPlacesRepository
import com.example.budgetbuddy.database.transactions.ILocalTransactionsRepository
import com.example.budgetbuddy.model.NotificationData
import com.example.budgetbuddy.model.db.Place
import com.example.budgetbuddy.model.db.TransactionCategory
import com.example.budgetbuddy.model.db.TransactionType
import com.example.budgetbuddy.services.AuthService
import com.example.budgetbuddy.services.datastore.IDataStoreRepository
import com.example.budgetbuddy.ui.screens.places.map.MapScreenUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class AddEditTransactionViewModel @Inject constructor(
    private val repository: ILocalTransactionsRepository,
    private val placeRepository: ILocalPlacesRepository,
    private val authService: AuthService,
    private val dataStoreRepository: IDataStoreRepository
) : ViewModel(), AddEditTransactionActions {

    private val _addEditTransactionUIState: MutableStateFlow<AddEditTransactionUIState> =
        MutableStateFlow(AddEditTransactionUIState.Loading)

    val addEditTransactionUIState = _addEditTransactionUIState.asStateFlow()

    private var data = AddEditTransactionScreenData()

    init {
        if (authService.getCurrentUser() == null) {
            _addEditTransactionUIState.update {
                AddEditTransactionUIState.UserNotAuthorized
            }
        }
    }

    override fun saveTransaction(){
        val type = data.transaction.type
        val category = data.transaction.category
        val note = data.transaction.note
        val date = data.transaction.date
        val place = data.transaction.placeId
        val price = data.transaction.price

        authService.getUserID()?.let { userId ->
            data.transaction.userId = userId
        }

        if (price != 0.0) {
            viewModelScope.launch {
                if (data.transaction.id != null){
                    repository.update(data.transaction)
                } else {
                    repository.insert(data.transaction)
                }

                dataStoreRepository.saveNotificationData(
                    NotificationData(
                        true,
                        R.string.add_edit_transaction_success, true
                    )
                )
            }

            _addEditTransactionUIState.update {
                AddEditTransactionUIState.TransactionSaved
            }
        }
        else {
            _addEditTransactionUIState.update {
                AddEditTransactionUIState.TransactionChanged(data)
            }

            data.transactionPriceError = R.string.cannot_be_empty
        }
    }

    override fun loadTransaction(id: Long?) {
        authService.getUserID()?.let { userId ->
            if (id != null){
                viewModelScope.launch {
                    repository.getTransaction(id, userId).collect { transaction ->
                        if (transaction != null) {
                            data.transaction = transaction
                            _addEditTransactionUIState.update {
                                AddEditTransactionUIState.TransactionChanged(data)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun loadPlaces() {
        val userID = authService.getUserID()
        if (userID != null) {
            viewModelScope.launch {
                placeRepository.getAllByUser(userID).collect {data ->
                    _addEditTransactionUIState.update {
                        AddEditTransactionUIState.PlacesLoaded(data)
                    }
                }
            }
        }
    }

    override fun deleteTransaction() {
        // TODO
    }

    override fun onReceiptChange() {
        // TODO
    }

    override fun onTransactionPriceChange(price: Double) {
        data.transaction.price = price
        _addEditTransactionUIState.update {
            AddEditTransactionUIState.TransactionChanged(data)
        }
    }

    override fun onTransactionCategoryChanged(category: TransactionCategory) {
        data.transaction.category = category
        _addEditTransactionUIState.update {
            AddEditTransactionUIState.TransactionChanged(data)
        }
    }

    override fun onTransactionCurrencyChange(currency: String) {
        data.transaction.currency = currency
        _addEditTransactionUIState.update {
            AddEditTransactionUIState.TransactionChanged(data)
        }
    }

    override fun onTransactionNoteChange(note: String) {
        data.transaction.note = note
        _addEditTransactionUIState.update {
            AddEditTransactionUIState.TransactionChanged(data)
        }
    }

    override fun onTransactionDateChange(date: Long?) {
        data.transaction.date = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(date ?: System.currentTimeMillis()),
            ZoneId.systemDefault()
        )
        _addEditTransactionUIState.update {
            AddEditTransactionUIState.TransactionChanged(data)
        }
    }

    override fun onTransactionPlaceChange(place: Place) {
        data.transaction.placeId = place.id
        _addEditTransactionUIState.update {
            AddEditTransactionUIState.TransactionChanged(data)
        }
    }

    override fun onTransactionTypeChange(type: String) {
        data.transaction.type = type
        _addEditTransactionUIState.update {
            AddEditTransactionUIState.TransactionChanged(data)
        }
    }
}
