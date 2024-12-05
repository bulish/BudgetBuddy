package com.example.budgetbuddy.ui.screens.transactions.list

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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsListViewModel @Inject constructor(
    private val repository: ILocalTransactionsRepository,
    private val authService: AuthService,
    private val dataStoreRepository: IDataStoreRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<TransactionsListUIState> = MutableStateFlow(value = TransactionsListUIState.Loading)
    val uiState: StateFlow<TransactionsListUIState> get() = _uiState.asStateFlow()

    private val numberOfMarkers = 2

    init {
        if (authService.getCurrentUser() == null) {
            _uiState.update {
                TransactionsListUIState.UserNotAuthorized
            }
        }

        val userID = authService.getUserID()
        if (userID != null) {
            viewModelScope.launch {
                repository.getAllByUser(userID).collect {data ->
                    _uiState.update {
                        TransactionsListUIState.DataLoaded(data)
                    }
                }
            }
        }
    }

fun deletePlace(id: Long?) {
        // TODO
    }

}





