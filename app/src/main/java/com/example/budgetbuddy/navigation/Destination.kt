package com.example.budgetbuddy.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destination(val route: String, val icon: ImageVector? = null, val title: String) {
    object LoginScreen : Destination("login", null, "Login")
    object SignUpScreen : Destination("sign_up", null, "Sign Up")
    object ResetPasswordScreen : Destination("reset_password", null, "Reset Password")
    object HomeScreen : Destination("home", Icons.Default.Home, "Home")
    object MapScreen : Destination("map", Icons.Default.Place, "Map")
    object AddEditPlaceScreen : Destination("add_edit_place", null, "Add/Edit Place")
    object SettingsScreen : Destination("settings", Icons.Default.Settings, "Settings")
    object TransactionsList : Destination("transactions_list", Icons.Default.List, "Transactions")
    object AddEditTransaction : Destination("add_edit_transaction", null, "Add/Edit Transaction")
    object TransactionDetail : Destination("transaction_detail", null, "Transaction Detail")
}
