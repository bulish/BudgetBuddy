package com.example.budgetbuddy.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetbuddy.model.PrimaryColor
import com.example.budgetbuddy.services.datastore.IDataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val dataStoreRepository: IDataStoreRepository
) : ViewModel() {

    private var _isDarkMode = MutableStateFlow<Boolean>(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode

    private val _primaryColor = MutableStateFlow<PrimaryColor>(PrimaryColor.GREEN)
    val primaryColor: StateFlow<PrimaryColor> = _primaryColor

    init {
        viewModelScope.launch {
            dataStoreRepository.getIsDarkTheme().collect {
                _isDarkMode.value = it
            }
        }

        viewModelScope.launch {
            dataStoreRepository.getPrimaryColor().collect {
                _primaryColor.value = PrimaryColor.fromString(it)
            }
        }
    }

}
