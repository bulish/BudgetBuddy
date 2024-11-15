package com.example.budgetbuddy.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetbuddy.database.transactions.ILocalTransactionsRepository
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

    init {
        if (authService.getCurrentUser() == null) {
            _homeScreenUIState.update {
                HomeScreenUIState.UserNotAuthorized
            }
        }
    }

    override fun loadTransactions() {
        val userID = authService.getUserID()

        if (userID != null) {
            viewModelScope.launch {
                repository.getAllByUser(userId = userID).collect {transactions ->
                    _homeScreenUIState.update {
                        HomeScreenUIState.Success(transactions = transactions)
                    }
                }

            }
        }
    }
}
