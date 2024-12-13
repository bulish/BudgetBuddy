package com.example.budgetbuddy.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetbuddy.database.transactions.ILocalTransactionsRepository
import com.example.budgetbuddy.model.db.TransactionType
import com.example.budgetbuddy.services.AuthService
import com.example.budgetbuddy.services.datastore.IDataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: ILocalTransactionsRepository,
    private val authService: AuthService,
    private val dataStoreRepository: IDataStoreRepository
) : ViewModel(), HomeScreenActions {

    private val _homeScreenUIState: MutableStateFlow<HomeScreenUIState> =
        MutableStateFlow(value = HomeScreenUIState.Loading)

    val homeScreenUIState = _homeScreenUIState.asStateFlow()

    private var _activeCurrency = MutableStateFlow<String>("")
    val activeCurrency: StateFlow<String> = _activeCurrency

    init {
        if (authService.getCurrentUser() == null) {
            _homeScreenUIState.update {
                HomeScreenUIState.UserNotAuthorized
            }
        }

        viewModelScope.launch {
            dataStoreRepository.getCurrency().collect {
                _activeCurrency.value = it
            }
        }
    }

    override fun loadTransactions() {
        val userID = authService.getUserID()

        if (userID != null) {
            viewModelScope.launch {
                repository.getAllByUser(userId = userID).collect {transactions ->
                    val totalSum = transactions.sumOf { transaction ->
                        when (transaction.type) {
                            TransactionType.INCOME.value -> transaction.price
                            TransactionType.EXPENSE.value -> -transaction.price
                            else -> 0.0
                        }
                    }

                    _homeScreenUIState.update {
                        HomeScreenUIState.Success(transactions, totalSum)
                    }
                }

            }
        }
    }

    override fun changeCurrency(currency: String) {
        viewModelScope.launch {
            _activeCurrency.update {
                currency
            }

            dataStoreRepository.setCurrency(activeCurrency.value)
        }
    }
}
