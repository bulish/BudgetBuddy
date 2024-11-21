package com.example.budgetbuddy.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.budgetbuddy.BuildConfig
import com.example.budgetbuddy.navigation.Destination
import com.example.budgetbuddy.navigation.NavGraph
import com.example.budgetbuddy.ui.theme.BudgetBuddyTheme
import com.google.android.libraries.places.api.Places

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, BuildConfig.MAP_API_KEY)
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BudgetBuddyTheme {
                NavGraph(startDestination = Destination.LoginScreen.route)
            }
        }
    }
}
