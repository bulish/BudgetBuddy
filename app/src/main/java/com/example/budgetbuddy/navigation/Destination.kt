package com.example.budgetbuddy.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.budgetbuddy.R

sealed class Destination(val route: String, val icon: ImageVector? = null, val title: Int) {
    object LoginScreen : Destination("login", null, R.string.login)
    object SignUpScreen : Destination("sign_up", null, R.string.sign_up)
    object ResetPasswordScreen : Destination("reset_password", null, R.string.reset_password)
    object HomeScreen : Destination("home", Icons.Default.Home, R.string.home)
    object MapScreen : Destination("map", Icons.Default.Place, R.string.map)
    object AddEditPlaceScreen : Destination("add_edit_place", null, R.string.add_place_title)
    object SettingsScreen : Destination("settings", Icons.Default.Settings, R.string.settings)
    object TransactionsList : Destination("transactions_list", Icons.Default.List, R.string.money)
    object AddEditTransaction : Destination("add_edit_transaction", null, R.string.add_transaction_title)
    object TransactionDetail : Destination("transaction_detail", null, R.string.transactions)
    object GreetingsScreen : Destination("greetings", null, 0)
}
