package com.example.budgetbuddy.ui.screens.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetbuddy.R
import com.example.budgetbuddy.communication.CommunicationResult
import com.example.budgetbuddy.communication.IExchangeRateRemoteRepository
import com.example.budgetbuddy.model.PrimaryColor
import com.example.budgetbuddy.services.AuthService
import com.example.budgetbuddy.services.IAuthService
import com.example.budgetbuddy.services.datastore.IDataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authService: IAuthService,
    val dataStoreRepository: IDataStoreRepository,
    private val exchangeRateRemoteRepository: IExchangeRateRemoteRepository
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
                SettingsUIState.UserNotAuthorized(null)
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
                !it
            }

            val updatedMode = _isDarkMode.value
            dataStoreRepository.setDarkTheme(updatedMode)
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
            SettingsUIState.UserNotAuthorized(R.string.logout_success)
        }
    }

    override fun getUserInformation() {
        _settingsUIState.update {
            SettingsUIState.Success(authService.getCurrentUser())
        }
    }

    override fun getCurrencyData() {
        viewModelScope.launch {
            dataStoreRepository.getCurrencies().collect { dataStoreData ->
                if (dataStoreData != null) {
                    Log.d("dataStoreData", "${dataStoreData}")
                    _settingsUIState.update {
                        SettingsUIState.CurrencyLoaded(dataStoreData)
                    }
                } else {
                    val result = withContext(Dispatchers.IO) {
                        exchangeRateRemoteRepository.getCurrentCurrency("CZK")
                    }

                    when(result) {
                        is CommunicationResult.ConnectionError -> {
                            _settingsUIState.update {
                                SettingsUIState
                                    .Error(SettingsScreenError(R.string.no_internet_connection))
                            }
                        }
                        is CommunicationResult.Error -> {
                            _settingsUIState.update {
                                SettingsUIState
                                    .Error(SettingsScreenError(R.string.something_went_wrong))
                            }
                        }
                        is CommunicationResult.Exception -> {
                            _settingsUIState.update {
                                SettingsUIState
                                    .Error(SettingsScreenError(R.string.something_went_wrong))
                            }
                        }
                        is CommunicationResult.Success -> {
                            _settingsUIState.update {
                                SettingsUIState.CurrencyLoaded(result.data.conversion_rates)
                            }

                            result.data.conversion_rates?.let {
                                dataStoreRepository.setCurrencies(it)
                            }
                        }
                    }
                }
            }
        }
    }
}
