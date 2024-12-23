package com.example.budgetbuddy.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetbuddy.R
import com.example.budgetbuddy.communication.CommunicationResult
import com.example.budgetbuddy.communication.ExchangeRateResponse
import com.example.budgetbuddy.communication.IExchangeRateRemoteRepository
import com.example.budgetbuddy.database.transactions.ILocalTransactionsRepository
import com.example.budgetbuddy.extensions.toFormattedString
import com.example.budgetbuddy.model.db.Transaction
import com.example.budgetbuddy.model.db.TransactionType
import com.example.budgetbuddy.services.AuthService
import com.example.budgetbuddy.services.IAuthService
import com.example.budgetbuddy.services.datastore.IDataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: ILocalTransactionsRepository,
    private val authService: IAuthService,
    private val dataStoreRepository: IDataStoreRepository,
    private val exchangeRateRemoteRepository: IExchangeRateRemoteRepository
) : ViewModel(), HomeScreenActions {

    private val _homeScreenUIState: MutableStateFlow<HomeScreenUIState> =
        MutableStateFlow(value = HomeScreenUIState.Loading)

    val homeScreenUIState = _homeScreenUIState.asStateFlow()

    private var _activeCurrency = MutableStateFlow<String>("")
    val activeCurrency: StateFlow<String> = _activeCurrency

    private var _conversionRates = MutableStateFlow<Map<String, Double>?>(null)

    init {
        if (authService.getCurrentUser() == null) {
            _homeScreenUIState.update {
                HomeScreenUIState.UserNotAuthorized
            }
        }

        viewModelScope.launch {
            dataStoreRepository.getCurrency().collect {
                _activeCurrency.value = it
                getCurrencyData()
            }
        }

        viewModelScope.launch {
            dataStoreRepository.getCurrencies().collect {
                _conversionRates.value = it
            }
        }
    }

    override fun loadTransactions() {
        val userID = authService.getUserID()

        if (userID != null) {
            viewModelScope.launch {
                repository.getAllByUser(userId = userID, null).collect { transactions ->
                    val conversionRates = dataStoreRepository.getCurrencies().first()
                    val totalSumInCurrentCurrency = transactions.sumOf { transaction ->
                        val activeRate = conversionRates?.get(activeCurrency.value) ?: 1.0

                        when (transaction.type) {
                            TransactionType.INCOME.value -> transaction.price * activeRate
                            TransactionType.EXPENSE.value -> -transaction.price * activeRate
                            else -> 0.0
                        }
                    }

                    _homeScreenUIState.update {
                        HomeScreenUIState.Success(transactions, totalSumInCurrentCurrency)
                    }
                }
            }
        }
    }

    override fun changeCurrency(currency: String) {
        viewModelScope.launch {
            _activeCurrency.update { currency }
            dataStoreRepository.setCurrency(activeCurrency.value)
            getCurrencyData()
        }
    }

    override fun getCurrencyData() {
        viewModelScope.launch {
            dataStoreRepository.getCurrencies().collect { dataStoreData ->
               if (dataStoreData != null) {
                   _homeScreenUIState.update {
                       HomeScreenUIState.CurrencyLoaded(dataStoreData)
                   }
               } else {
                   val result = withContext(Dispatchers.IO) {
                       exchangeRateRemoteRepository.getCurrentCurrency("CZK")
                   }

                   when(result) {
                       is CommunicationResult.ConnectionError -> {
                           _homeScreenUIState.update {
                               HomeScreenUIState
                                   .Error(HomeScreenError(R.string.no_internet_connection))
                           }
                       }
                       is CommunicationResult.Error -> {
                           _homeScreenUIState.update {
                               HomeScreenUIState
                                   .Error(HomeScreenError(R.string.something_went_wrong))
                           }
                       }
                       is CommunicationResult.Exception -> {
                           _homeScreenUIState.update {
                               HomeScreenUIState
                                   .Error(HomeScreenError(R.string.something_went_wrong))
                           }
                       }
                       is CommunicationResult.Success -> {
                           _homeScreenUIState.update {
                               HomeScreenUIState.CurrencyLoaded(result.data.conversion_rates)
                           }

                           result.data.conversion_rates?.let {
                               dataStoreRepository.setCurrencies(it)
                               _conversionRates.value = it
                           }
                       }
                   }
               }
            }
        }
    }

    override fun transformTransactionPrice(transaction: Transaction): String {
        val currency = activeCurrency.value

        _activeCurrency.update {
            currency
        }

        val activeRate = _conversionRates.value?.get(currency) ?: 1.0
        val value =  (transaction.price * activeRate).toFormattedString()
        return "$value $currency"
    }
}
