package com.example.budgetbuddy.ui.screens.transactions.addEdit

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetbuddy.R
import com.example.budgetbuddy.database.places.ILocalPlacesRepository
import com.example.budgetbuddy.database.transactions.ILocalTransactionsRepository
import com.example.budgetbuddy.model.db.Place
import com.example.budgetbuddy.model.db.TransactionCategory
import com.example.budgetbuddy.services.AuthService
import com.example.budgetbuddy.services.IAuthService
import com.example.budgetbuddy.services.datastore.IDataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
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
    private val authService: IAuthService,
    private val dataStoreRepository: IDataStoreRepository,
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
        val price = data.transaction.price

        authService.getUserID()?.let { userId ->
            data.transaction.userId = userId
        }

        if (price != 0.0) {
            viewModelScope.launch {
                Log.d("new item", "${data.transaction}")

                if (data.transaction.id != null){
                    repository.update(data.transaction)
                } else {
                    repository.insert(data.transaction)
                }
            }

            _addEditTransactionUIState.update {
                AddEditTransactionUIState.TransactionSaved(R.string.add_edit_transaction_success)
            }
        }
        else {
            _addEditTransactionUIState.update {
                AddEditTransactionUIState.TransactionChanged(data)
            }

            data.transactionPriceError = R.string.cannot_be_empty
        }
    }

    fun initializeTransactionData(id: Long?) {
        authService.getUserID()?.let { userId ->
            viewModelScope.launch {
                try {
                    coroutineScope {
                        val transactionFlow = id?.let {
                            repository.getTransaction(it, userId)
                        } ?: flowOf(null)

                        val placesFlow = placeRepository.getAllByUser(userId).also { Log.d("PlacesFlow", "Places flow created") }
                        val currenciesFlow = dataStoreRepository.getCurrencies()
                            .filterNotNull()

                        combine(
                            transactionFlow,
                            placesFlow,
                            currenciesFlow.map { currencies ->
                                currencies.mapValues { it.value.toString() }
                            },
                        ) { transaction, places, currencies ->

                            if (transaction != null) {
                                data.transaction = transaction
                            }

                            CombinedTransactionData(
                                transaction = transaction,
                                places = places,
                                currencies = currencies
                            )
                        }.collect { combinedData ->
                            _addEditTransactionUIState.update {
                                AddEditTransactionUIState.TransactionDataLoaded(combinedData)
                            }
                        }
                    }
                } catch (e: Exception) {
                    _addEditTransactionUIState.update {
                        AddEditTransactionUIState.Error(
                            AddEditTransactionScreenError(R.string.something_went_wrong)
                        )
                    }
                }
            }
        } ?: run {
            _addEditTransactionUIState.update {
                AddEditTransactionUIState.UserNotAuthorized
            }
        }
    }

    override fun deleteTransaction() {
        viewModelScope.launch {
            repository.delete(data.transaction)
            _addEditTransactionUIState.update {
                AddEditTransactionUIState.TransactionDeleted
            }
        }
    }

    override fun onTransactionPriceChange(price: Double) {
        data.transaction.price = price
        _addEditTransactionUIState.update {
            AddEditTransactionUIState.TransactionChanged(data)
        }
    }

    override fun onTransactionCategoryChanged(category: TransactionCategory) {
        data.transaction.category = category.value
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
        val dateToUse = date ?: System.currentTimeMillis()
        data.transaction.date = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(dateToUse),
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
