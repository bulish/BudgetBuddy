package com.example.budgetbuddy.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.budgetbuddy.BuildConfig
import com.example.budgetbuddy.SplashScreenUiState
import com.example.budgetbuddy.SplashScreenViewModel
import com.example.budgetbuddy.navigation.Destination
import com.example.budgetbuddy.navigation.NavGraph
import com.example.budgetbuddy.ui.theme.BudgetBuddyTheme
import com.google.android.libraries.places.api.Places
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: SplashScreenViewModel by viewModels()

    private val _splashScreenState =
        MutableStateFlow<SplashScreenUiState>(SplashScreenUiState.Default)
    val splashScreenState: StateFlow<SplashScreenUiState> = _splashScreenState
    private val startDestination = mutableStateOf<String?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, BuildConfig.MAP_API_KEY)
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.splashScreenState.collect { value: SplashScreenUiState ->
                    when (value) {
                        is SplashScreenUiState.Default -> {
                            viewModel.checkAppState()
                        }

                        SplashScreenUiState.ContinueToApp -> {
                            startDestination.value = continueToApp()
                        }

                        is SplashScreenUiState.RunForAFirstTime -> {
                            startDestination.value = runAppIntro()
                        }
                    }
                }
            }
        }

        setContent {
            BudgetBuddyTheme {
                startDestination.value?.let { destination ->
                    NavGraph(
                        startDestination = destination
                    )
                }
            }
        }
    }

    private fun continueToApp(): String {
        return viewModel.continueToApp()
    }

    private fun runAppIntro(): String {
        return Destination.GreetingsScreen.route
    }

}
