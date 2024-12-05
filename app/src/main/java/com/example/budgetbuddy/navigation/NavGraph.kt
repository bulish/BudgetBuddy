package com.example.budgetbuddy.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.budgetbuddy.model.Location
import com.example.budgetbuddy.ui.screens.auth.login.LoginScreen
import com.example.budgetbuddy.ui.screens.auth.resetPassword.ResetPasswordScreen
import com.example.budgetbuddy.ui.screens.auth.signUp.SignUpScreen
import com.example.budgetbuddy.ui.screens.home.HomeScreen
import com.example.budgetbuddy.ui.screens.places.addEditPlace.AddEditPlaceScreen
import com.example.budgetbuddy.ui.screens.places.addEditPlace.AddEditTransactionScreen
import com.example.budgetbuddy.ui.screens.places.map.MapScreen
import com.example.budgetbuddy.ui.screens.settings.SettingsScreen
import com.example.budgetbuddy.ui.screens.transactions.addEdit.AddEditTransactionScreen
import com.example.budgetbuddy.ui.screens.transactions.detail.DetailTransactionScreen
import com.example.budgetbuddy.ui.screens.transactions.list.TransactionsListScreen
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    navigationRouter: INavigationRouter = remember {
        NavigationRouterImpl(navController)
    },
    startDestination: String,
    context: Context = LocalContext.current
){

    NavHost(navController = navController, startDestination = startDestination){

        composable(Destination.LoginScreen.route){
            LoginScreen(navigationRouter = navigationRouter)
        }

        composable(Destination.SignUpScreen.route){
            SignUpScreen(navigationRouter = navigationRouter)
        }

        composable(Destination.ResetPasswordScreen.route){
            ResetPasswordScreen(navigationRouter = navigationRouter)
        }

        composable(Destination.HomeScreen.route){
            HomeScreen(navigationRouter = navigationRouter)
        }

        composable(Destination.MapScreen.route){
            MapScreen(navigationRouter = navigationRouter)
        }

        composable(Destination.AddEditPlaceScreen.route + "/{id}",
            arguments = listOf(
                navArgument(name = "id"){
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )){
            val id = it.arguments?.getLong("id")
            AddEditPlaceScreen(navigationRouter = navigationRouter, id = id, context = context)
        }

        composable(Destination.AddEditPlaceScreen.route){
            AddEditPlaceScreen(navigationRouter = navigationRouter, id = null, context = context)
        }

        composable(Destination.SettingsScreen.route){
            SettingsScreen(
                navigationRouter = navigationRouter,
                context = context
            )
        }

        composable(Destination.TransactionsList.route){
            TransactionsListScreen(navigationRouter = navigationRouter)
        }

        composable(Destination.AddEditTransaction.route + "/{id}",
            arguments = listOf(
                navArgument(name = "id"){
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )) {
            val id = it.arguments?.getLong("id")
            AddEditTransactionScreen(navigationRouter = navigationRouter, context = context, id = id)
        }

        composable(Destination.AddEditTransaction.route){
            AddEditTransactionScreen(navigationRouter = navigationRouter, context = context, id = null)
        }

        composable(Destination.TransactionDetail.route + "/{id}",
            arguments = listOf(
                navArgument(name = "id"){
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ) {
            val id = it.arguments?.getLong("id")
            DetailTransactionScreen(navigationRouter = navigationRouter, id = id)
        }
    }
}
