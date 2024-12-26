package com.example.budgetbuddy.auth

import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.budgetbuddy.navigation.Destination
import com.example.budgetbuddy.navigation.NavGraph
import com.example.budgetbuddy.ui.activity.MainActivity
import com.example.budgetbuddy.ui.screens.auth.login.*
import com.example.budgetbuddy.ui.screens.auth.resetPassword.*
import com.example.budgetbuddy.ui.screens.greetings.TestTagGreetingsScreenSkip
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.MockKAnnotations
import junit.framework.TestCase.assertEquals
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
class UITestResetPassword {

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

        navigate(Destination.LoginScreen.route)

        composeRule.waitForIdle()

        with(composeRule) {
            Thread.sleep(3000)
            waitForIdle()

            val route = navController.currentDestination?.route
            val skipNodes = onAllNodesWithTag(TestTagGreetingsScreenSkip)
            if (skipNodes.fetchSemanticsNodes().isNotEmpty()) {
                skipNodes[0].performClick()
            }

            if (route == Destination.LoginScreen.route) {
                onNodeWithTag(TestTagLoginScreenEmailInput).assertExists()
                onNodeWithTag(TestTagLoginScreenPasswordInput).assertExists()
                onNodeWithTag(TestTagLoginScreenSubmitButton).assertExists()

                onNodeWithTag(TestTagLoginScreenEmailInput).performTextInput(email)
                onNodeWithTag(TestTagLoginScreenPasswordInput).performTextInput(password)
                onNodeWithTag(TestTagLoginScreenSubmitButton).performClick()

                waitForIdle()

                assertTrue(route == Destination.LoginScreen.route)
            }

            Thread.sleep(3000)
        }

        composeRule.activity.runOnUiThread {
            try {
                navController.navigate(Destination.LoginScreen.route)

                composeRule.waitForIdle()
            } catch (e: Exception) {
                println("Navigation failed: ${e.message}")
            }
        }

        composeRule.waitForIdle()
    }

    @Test
    fun test1_loginFormExists() {
        with(composeRule) {
            Thread.sleep(3000)
            waitForIdle()

            val route = navController.currentDestination?.route

            if (route == Destination.LoginScreen.route) {
                onNodeWithTag(TestTagLoginScreenForm).assertIsDisplayed()
                onNodeWithTag(TestTagLoginScreenEmailInput).assertIsDisplayed()
                onNodeWithTag(TestTagLoginScreenPasswordInput).assertIsDisplayed()
                onNodeWithTag(TestTagLoginScreenSubmitButton).assertIsDisplayed()
                onNodeWithTag(TestTagLoginScreenSignUpButton).assertIsDisplayed()
                onNodeWithTag(TestTagLoginScreenForgottenPassword).assertIsDisplayed()
            }
        }
    }

    @Test
    fun test2_resetPasswordExists() {
        with(composeRule) {
            Thread.sleep(3000)
            waitForIdle()

            val route = navController.currentDestination?.route

            if (route == Destination.LoginScreen.route) {
                onNodeWithTag(TestTagLoginScreenForgottenPassword)
                    .assertIsDisplayed()
                    .performClick()

                waitForIdle()

                onNodeWithTag(TestTagResetPasswordScreenTitle).assertExists()
            }
        }
    }

    @Test
    fun test3_submitValidForm() {
        with(composeRule) {
            Thread.sleep(3000)
            waitForIdle()

            val route = navController.currentDestination?.route

            if (route == Destination.LoginScreen.route) {
                onNodeWithTag(TestTagLoginScreenForgottenPassword)
                    .assertIsDisplayed()
                    .performClick()

                waitForIdle()

                onNodeWithTag(TestTagResetPasswordScreenTitle).assertExists()

                onNodeWithTag(TestTagResetPasswordScreenEmailInput)
                    .assertIsDisplayed()
                    .performTextInput("xbabicko@mendelu.cz")

                onNodeWithTag(TestTagResetPasswordScreenSubmitButton)
                    .assertIsDisplayed()
                    .performClick()

                waitForIdle()

                assertEquals(navController.currentDestination?.route, Destination.LoginScreen.route)
            }
        }
    }

    @Test
    fun test4_submitInvalidForm() {
        with(composeRule) {
            Thread.sleep(3000)
            waitForIdle()

            val route = navController.currentDestination?.route

            if (route == Destination.LoginScreen.route) {
                onNodeWithTag(TestTagLoginScreenForgottenPassword)
                    .assertIsDisplayed()
                    .performClick()

                waitForIdle()

                onNodeWithTag(TestTagResetPasswordScreenSubmitButton)
                    .assertIsDisplayed()
                    .performClick()

                waitForIdle()

                assertEquals(navController.currentDestination?.route, Destination.ResetPasswordScreen.route)
            }
        }
    }

    private fun navigate(route: String) {
        composeRule.activity.setContent {
            MaterialTheme {
                navController = rememberNavController()
                NavGraph(navController = navController, startDestination = route)
            }
        }
    }
}