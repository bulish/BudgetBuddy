package com.example.budgetbuddy.ui.screens.transactions.list

import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope
import com.example.budgetbuddy.database.transactions.ILocalTransactionsRepository
import com.example.budgetbuddy.model.db.Transaction
import com.example.budgetbuddy.model.db.TransactionCategory
import com.example.budgetbuddy.model.db.TransactionType
import com.example.budgetbuddy.services.AuthService
import com.example.budgetbuddy.services.IAuthService
import com.example.budgetbuddy.services.datastore.IDataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsListViewModel @Inject constructor(
    private val repository: ILocalTransactionsRepository,
    private val authService: IAuthService,
    private val dataStoreRepository: IDataStoreRepository
) : ViewModel(), TransactionListScreenActions {

    private val _uiState: MutableStateFlow<TransactionsListUIState> = MutableStateFlow(value = TransactionsListUIState.Loading)
    val uiState: StateFlow<TransactionsListUIState> get() = _uiState.asStateFlow()

    private var _activeCurrency = MutableStateFlow<String>("")
    val activeCurrency: StateFlow<String> = _activeCurrency

    private var _conversionRates = MutableStateFlow<Map<String, Double>?>(null)

    init {
        if (authService.getCurrentUser() == null) {
            _uiState.update {
                TransactionsListUIState.UserNotAuthorized
            }
        }

        viewModelScope.launch {
            dataStoreRepository.getCurrencies().collect {
                _conversionRates.value = it
            }
        }
    }

    override fun getAllTransactions(category: TransactionCategory?) {
        val userID = authService.getUserID()
        if (userID != null) {
            viewModelScope.launch {
                repository.getAllByUser(userID, category).collect {data ->
                    val conversionRates = dataStoreRepository.getCurrencies().first()
                    val currency = dataStoreRepository.getCurrency().first()
                    _activeCurrency.update {
                        currency
                    }

                    val totalSumInCurrentCurrency = data.sumOf { transaction ->
                        val activeRate = conversionRates?.get(currency) ?: 1.0

                        when (transaction.type) {
                            TransactionType.INCOME.value -> transaction.price * activeRate
                            TransactionType.EXPENSE.value -> -transaction.price * activeRate
                            else -> 0.0
                        }
                    }

                    _uiState.update {
                        TransactionsListUIState.DataLoaded(
                            data,
                            totalSumInCurrentCurrency,
                            activeCurrency.value
                        )
                    }
                }
            }
        }
    }

    override fun transformTransactionPrice(transaction: Transaction): String {
        val conversionRates = _conversionRates.value
        val currency = activeCurrency.value

        _activeCurrency.update {
            currency
        }

        val activeRate = conversionRates?.get(currency) ?: 1.0
        val value =  transaction.price * activeRate
        return "$value $currency"
    }
}
