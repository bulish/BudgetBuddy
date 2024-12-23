package com.example.budgetbuddy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetbuddy.navigation.Destination
import com.example.budgetbuddy.services.AuthService
import com.example.budgetbuddy.services.datastore.IDataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val dataStoreRepository: IDataStoreRepository,
    private val auth: AuthService
) : ViewModel() {
    private val _splashScreenState =
        MutableStateFlow<SplashScreenUiState>(SplashScreenUiState.Default)
    val splashScreenState: StateFlow<SplashScreenUiState> = _splashScreenState

    fun checkAppState() {
        viewModelScope.launch {
            dataStoreRepository.getFirstRun().collect {
                if (it) {
                    _splashScreenState.value = SplashScreenUiState.RunForAFirstTime
                } else {
                    _splashScreenState.value = SplashScreenUiState.ContinueToApp
                }
            }
        }
    }

    fun continueToApp(): String {
        return if (auth.getCurrentUser() != null) Destination.HomeScreen.route else Destination.LoginScreen.route
    }
}
