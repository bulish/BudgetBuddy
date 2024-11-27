package com.example.budgetbuddy.navigation

import androidx.navigation.NavController

interface INavigationRouter {
    fun navigateToLoginScreen()
    fun navigateToSignUpScreen()
    fun navigateToResetPasswordScreen()
    fun navigateToHomeScreen()
    fun navigateToMapScreen(latitude: Double?, longitude: Double?)
    fun navigateToAddEditPlaceScreen(id: Long?)
    fun navigateToSettingsScreen()
    fun navigateToTransactionsListScreen()
    fun navigateToTransactionDetailScreen(id: Long?)
    fun navigateToAddEditTransactionScreen(id: Long?)
    fun returnBack()
    fun getNavController(): NavController
    fun navigateTabs(route: String)
}
