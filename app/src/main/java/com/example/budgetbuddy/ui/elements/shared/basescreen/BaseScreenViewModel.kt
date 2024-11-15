package com.example.budgetbuddy.ui.elements.shared.basescreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetbuddy.model.NotificationData
import com.example.budgetbuddy.services.datastore.IDataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BaseScreenViewModel @Inject constructor(
    private val dataStoreRepository: IDataStoreRepository
) : ViewModel() {

    private var _notificationData = MutableStateFlow<NotificationData?>(null)
    val notificationData: StateFlow<NotificationData?> = _notificationData

    init {
        observeNotificationData()
    }

    private fun observeNotificationData() {
        viewModelScope.launch {
            dataStoreRepository.getNotificationData().collect {
                _notificationData.value = it
            }
        }
    }
}
