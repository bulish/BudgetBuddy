package com.example.budgetbuddy.auth

import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
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
import com.example.budgetbuddy.ui.screens.greetings.TestTagGreetingsScreenSkip
import com.example.budgetbuddy.ui.screens.home.TestTagHomeScreenAddMoneyButton
import com.example.budgetbuddy.ui.screens.home.TestTagHomeScreenTransactionsFAB
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
            Thread.sleep(3000)
            waitForIdle()

            val route = navController.currentDestination?.route
            val skipNodes = onAllNodesWithTag(TestTagGreetingsScreenSkip)
            if (skipNodes.fetchSemanticsNodes().isNotEmpty()) {
                skipNodes[0].performClick()
            }

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
    fun test2_login_successful() {
        val email = "test@test.com"
        val password = "Test12345"

        launchLoginScreenWithNavigation()

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
    }

    /*
    @Test
    fun test3_login_successful() {

        runBlocking {
            val mockApiService = mockk<FakeExchangeRateRemoteRepositoryImpl>(relaxed = true)
            val currencyMock = ServerMock.data

            `when`(mockApiService.getCurrentCurrency("USD")).thenReturn(CommunicationResult.Success(currencyMock))


            val result = mockApiService.getCurrentCurrency("USD")
            assert(result is CommunicationResult.Success)
            assert((result as CommunicationResult.Success).data.base_code == "USD")
        }
    }*/

    private fun launchLoginScreenWithNavigation() {
        composeRule.activity.setContent {
            MaterialTheme {
                navController = rememberNavController()
                NavGraph(navController = navController, startDestination = Destination.LoginScreen.route)
            }
        }
    }
}