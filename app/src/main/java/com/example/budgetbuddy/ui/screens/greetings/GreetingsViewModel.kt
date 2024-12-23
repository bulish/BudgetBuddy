package com.example.budgetbuddy.ui.screens.greetings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetbuddy.services.datastore.IDataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GreetingsViewModel @Inject constructor(
    private val dataStoreRepository: IDataStoreRepository,

    ) : ViewModel() {

    fun setFirstRun() {
        viewModelScope.launch {
            dataStoreRepository.setFirstRun()
        }
    }

}
