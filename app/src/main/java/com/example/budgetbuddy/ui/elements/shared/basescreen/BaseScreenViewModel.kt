package com.example.budgetbuddy.ui.elements.shared.basescreen

import androidx.lifecycle.ViewModel
import com.example.budgetbuddy.services.datastore.IDataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BaseScreenViewModel @Inject constructor(
    private val dataStoreRepository: IDataStoreRepository
) : ViewModel() {


}
