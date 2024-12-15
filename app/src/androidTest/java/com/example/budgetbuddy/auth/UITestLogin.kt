package com.example.budgetbuddy.auth

import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.budgetbuddy.navigation.Destination
import com.example.budgetbuddy.navigation.NavGraph
import com.example.budgetbuddy.ui.activity.MainActivity
import com.example.budgetbuddy.ui.screens.auth.login.TestTagLoginScreenEmailInput
import com.example.budgetbuddy.ui.screens.auth.login.TestTagLoginScreenForgottenPassword
import com.example.budgetbuddy.ui.screens.auth.login.TestTagLoginScreenForm
import com.example.budgetbuddy.ui.screens.auth.login.TestTagLoginScreenPasswordInput
import com.example.budgetbuddy.ui.screens.auth.login.TestTagLoginScreenSignUpButton
import com.example.budgetbuddy.ui.screens.auth.login.TestTagLoginScreenSubmitButton
import com.example.budgetbuddy.ui.screens.transactions.list.TestTagTransactionsListScreenTitle
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters

@ExperimentalCoroutinesApi
@HiltAndroidTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class UITestLogin {

    private lateinit var navController: NavHostController

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun test1_login_form_exists() {
        launchLoginScreenWithNavigation()
        with(composeRule) {
            onNodeWithTag(TestTagLoginScreenForm).assertIsDisplayed()
            onNodeWithTag(TestTagLoginScreenEmailInput).assertIsDisplayed()
            onNodeWithTag(TestTagLoginScreenPasswordInput).assertIsDisplayed()
            onNodeWithTag(TestTagLoginScreenSubmitButton).assertIsDisplayed()
            onNodeWithTag(TestTagLoginScreenSignUpButton).assertIsDisplayed()
            onNodeWithTag(TestTagLoginScreenForgottenPassword).assertIsDisplayed()
            Thread.sleep(1000)
        }
    }

    @Test
    fun test2_login_successful() {
        val email = "test@test.com"
        val password = "Test12345"

        launchLoginScreenWithNavigation()

        with(composeRule) {
            onNodeWithTag(TestTagLoginScreenEmailInput).assertExists()
            onNodeWithTag(TestTagLoginScreenPasswordInput).assertExists()
            onNodeWithTag(TestTagLoginScreenSubmitButton).assertExists()

            onNodeWithTag(TestTagLoginScreenEmailInput).performTextInput(email)
            onNodeWithTag(TestTagLoginScreenPasswordInput).performTextInput(password)
            onNodeWithTag(TestTagLoginScreenSubmitButton).performClick()

            waitForIdle()

            val route = navController.currentDestination?.route
            Thread.sleep(6000)
            assertTrue(route == Destination.LoginScreen.route)
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