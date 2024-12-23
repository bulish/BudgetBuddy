package com.example.budgetbuddy

sealed class SplashScreenUiState {
    object Default : SplashScreenUiState()
    object ContinueToApp : SplashScreenUiState()
    object RunForAFirstTime : SplashScreenUiState()
}
