package com.example.budgetbuddy

import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.budgetbuddy.database.transactions.ILocalTransactionsRepository
import com.example.budgetbuddy.database.transactions.LocalTransactionsRepositoryImpl
import com.example.budgetbuddy.database.transactions.TransactionsDao
import com.example.budgetbuddy.model.db.Transaction
import com.example.budgetbuddy.model.db.TransactionCategory
import com.example.budgetbuddy.model.db.TransactionType
import com.example.budgetbuddy.navigation.Destination
import com.example.budgetbuddy.navigation.NavGraph
import com.example.budgetbuddy.ui.activity.MainActivity
import com.example.budgetbuddy.ui.screens.auth.login.TestTagLoginScreenEmailInput
import com.example.budgetbuddy.ui.screens.auth.login.TestTagLoginScreenPasswordInput
import com.example.budgetbuddy.ui.screens.auth.login.TestTagLoginScreenSubmitButton
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters
import org.mockito.ArgumentMatchers.any
import io.mockk.mockk
import io.mockk.every
import io.mockk.mockkObject
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@HiltAndroidTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class UITestSettingsView {

    private lateinit var navController: NavHostController

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        MockKAnnotations.init(this, relaxed = true)

        val email = "test@test.com"
        val password = "Test12345"

        // Initialize navController here by launching the screen with navigation
        launchLoginScreenWithNavigation()

        composeRule.waitForIdle() // Wait for idle state before interaction

        // Perform login actions
        with(composeRule) {
            onNodeWithTag(TestTagLoginScreenEmailInput).performTextInput(email)
            onNodeWithTag(TestTagLoginScreenPasswordInput).performTextInput(password)
            onNodeWithTag(TestTagLoginScreenSubmitButton).performClick()
            waitForIdle() // Wait after the login action before the navigation
        }

        // Log before attempting navigation
        println("Attempting to navigate to Settings screen...")

        // Now navigate to the settings screen after ensuring login is completed
        composeRule.activity.runOnUiThread {
            try {
                navController.navigate(Destination.SettingsScreen.route)

                composeRule.waitForIdle()
            } catch (e: Exception) {
                println("Navigation failed: ${e.message}")
            }
        }

        composeRule.waitForIdle() // Wait for navigation to complete
    }

    @Test
    fun test1_settingsViewScreenIsLoaded() {
        with(composeRule) {
            // Ensure the settings screen is visible after navigation
            waitForIdle()
            onNodeWithTag("TestTagSettingsScreenTitle").assertIsDisplayed()
            waitForIdle()
        }
    }


    private fun launchLoginScreenWithNavigation() {
        composeRule.activity.setContent {
            MaterialTheme {
                navController = rememberNavController()
                NavGraph(navController = navController, startDestination = Destination.LoginScreen.route)
            }
        }
    }
}
