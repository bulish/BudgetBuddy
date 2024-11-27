package com.example.budgetbuddy.navigation

import androidx.navigation.NavController
import com.example.budgetbuddy.model.Location
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

class NavigationRouterImpl(private val navController: NavController) : INavigationRouter {

    override fun navigateToLoginScreen() {
        navController.navigate(Destination.LoginScreen.route)
    }

    override fun navigateToSignUpScreen() {
        navController.navigate(Destination.SignUpScreen.route)
    }

    override fun navigateToResetPasswordScreen() {
        navController.navigate(Destination.ResetPasswordScreen.route)
    }

    override fun navigateToHomeScreen() {
        navController.navigate(Destination.HomeScreen.route)
    }

    override fun navigateToMapScreen(latitude: Double?, longitude: Double?) {
        if (latitude != null && longitude != null){
            val moshi: Moshi = Moshi.Builder().build()
            val jsonAdapter: JsonAdapter<Location> = moshi.adapter(Location::class.java)
            navController.navigate(Destination.MapScreen.route + "/" +
                    jsonAdapter.toJson(Location(latitude, longitude))
            )
        } else {
            navController.navigate(Destination.MapScreen.route)
        }
    }

    override fun navigateToAddEditPlaceScreen(id: Long?) {
        if (id != null) {
            navController.navigate(Destination.AddEditPlaceScreen.route + "/" + id)
        } else {
            navController.navigate(Destination.AddEditPlaceScreen.route)
        }
    }

    override fun navigateToSettingsScreen() {
        navController.navigate(Destination.SettingsScreen.route)
    }

    override fun navigateToTransactionsListScreen() {
        navController.navigate(Destination.TransactionsList.route)
    }

    override fun navigateToTransactionDetailScreen(id: Long?) {
        navController.navigate(Destination.TransactionDetail.route + "/" + id)
    }

    override fun navigateToAddEditTransactionScreen(id: Long?) {
        if (id != null) {
            navController.navigate(Destination.AddEditTransaction.route + "/" + id)
        } else {
            navController.navigate(Destination.AddEditTransaction.route)
        }
    }

    override fun returnBack() {
        navController.popBackStack()
    }

    override fun getNavController(): NavController {
        return navController
    }

    override fun navigateTabs(route: String) {
        navController.navigate(route)
    }
}
