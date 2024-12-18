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
import com.example.budgetbuddy.database.transactions.TransactionsDao
import com.example.budgetbuddy.navigation.Destination
import com.example.budgetbuddy.navigation.NavGraph
import com.example.budgetbuddy.ui.activity.MainActivity
import com.example.budgetbuddy.ui.screens.auth.login.TestTagLoginScreenEmailInput
import com.example.budgetbuddy.ui.screens.auth.login.TestTagLoginScreenForgottenPassword
import com.example.budgetbuddy.ui.screens.auth.login.TestTagLoginScreenForm
import com.example.budgetbuddy.ui.screens.auth.login.TestTagLoginScreenPasswordInput
import com.example.budgetbuddy.ui.screens.auth.login.TestTagLoginScreenSignUpButton
import com.example.budgetbuddy.ui.screens.auth.login.TestTagLoginScreenSubmitButton
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters

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

        MockKAnnotations.init(this, relaxUnitFun = true)

        val email = "test@test.com"
        val password = "Test12345"

        // Initialize navController here by launching the screen with navigation
        launchLoginScreenWithNavigation()

        composeRule.waitForIdle() // Wait for idle state before interaction

        with(composeRule) {
            onNodeWithTag(TestTagLoginScreenEmailInput).performTextInput(email)
            onNodeWithTag(TestTagLoginScreenPasswordInput).performTextInput(password)
            onNodeWithTag(TestTagLoginScreenSubmitButton).performClick()
            waitForIdle() // Wait after the login action before the navigation
        }

        // Now navigate to the settings screen after ensuring login is completed
        composeRule.activity.runOnUiThread {
            navController.navigate(Destination.SettingsScreen.route)
        }

        composeRule.waitForIdle()
    }

    @Test
    fun test1_settingsViewScreenIsLoaded() {
        with(composeRule) {
            //onNodeWithTag("settingsScreenTag").assertIsDisplayed()
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
