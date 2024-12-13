package com.example.budgetbuddy.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetbuddy.R
import com.example.budgetbuddy.model.NotificationData
import com.example.budgetbuddy.model.PrimaryColor
import com.example.budgetbuddy.services.AuthService
import com.example.budgetbuddy.services.datastore.IDataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Thread.State
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authService: AuthService,
    private val dataStoreRepository: IDataStoreRepository
) : ViewModel(), SettingsActions {

    private val _settingsUIState: MutableStateFlow<SettingsUIState> =
        MutableStateFlow(value = SettingsUIState.Loading)

    val settingsUIState = _settingsUIState.asStateFlow()

    private var _activeCurrency = MutableStateFlow<String>("")
    val activeCurrency: StateFlow<String> = _activeCurrency

    private var _isDarkMode = MutableStateFlow<Boolean>(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode

    private var _primaryColor = MutableStateFlow<PrimaryColor>(PrimaryColor.GREEN)
    val primaryColor: StateFlow<PrimaryColor> = _primaryColor

    init {
        if (authService.getCurrentUser() == null) {
            _settingsUIState.update {
                SettingsUIState.UserNotAuthorized
            }
        }

        viewModelScope.launch {
            dataStoreRepository.getCurrency().collect {
                _activeCurrency.value = it
            }
        }

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

    override fun changeMode() {
        viewModelScope.launch {
            _isDarkMode.update {
                !isDarkMode.value
            }

            dataStoreRepository.setDarkTheme(isDarkMode.value)
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

    override fun changePrimaryColor(color: PrimaryColor) {
        viewModelScope.launch {
            _primaryColor.update {
                color
            }

            dataStoreRepository.setPrimaryColor(primaryColor.value)
        }
    }

    override fun signOut() {
        authService.signOut()
        _settingsUIState.update {
            SettingsUIState.UserNotAuthorized
        }

        viewModelScope.launch {
            dataStoreRepository.saveNotificationData(
                NotificationData(
                    show = true,
                    message = R.string.logout_success,
                    isSuccess = true
                )
            )
        }
    }

    override fun getUserInformation() {
        _settingsUIState.update {
            SettingsUIState.Success(authService.getCurrentUser())
        }
    }
}