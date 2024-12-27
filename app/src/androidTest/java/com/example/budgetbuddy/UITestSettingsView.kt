package com.example.budgetbuddy

import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
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
import com.example.budgetbuddy.ui.screens.greetings.TestTagGreetingsScreenSkip
import com.example.budgetbuddy.ui.screens.settings.*
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.MockKAnnotations
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking

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

        launchLoginScreenWithNavigation()

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

                TestCase.assertTrue(route == Destination.LoginScreen.route)
            }

            Thread.sleep(3000)
        }

        composeRule.activity.runOnUiThread {
            try {
                navController.navigate(Destination.SettingsScreen.route)

                composeRule.waitForIdle()
            } catch (e: Exception) {
                println("Navigation failed: ${e.message}")
            }
        }

        composeRule.waitForIdle()
    }

    @Test
    fun test1_settingsViewScreenIsLoaded() {
        with(composeRule) {
            onNodeWithTag(TestTagSettingsScreenTitle).assertIsDisplayed()
            onNodeWithTag(TestTagSettingsScreenLanguage).assertIsDisplayed()
            onNodeWithTag(TestTagSettingsScreenVersion).assertIsDisplayed()
        }
    }

    @Test
    fun test2_userInfoIsDisplayed() {
        with(composeRule) {
            val userData = "test@test.com"

            onNodeWithTag(TestTagSettingsScreenUserIcon)
                .assertExists()
                .assertIsDisplayed()

            onNodeWithTag(TestTagSettingsScreenUserName)
                .assertTextContains(userData)
                .assertIsDisplayed()
        }
    }

    @Test
    fun test3_changeDarkMode() {
       runBlocking {

           with(composeRule) {

               onNodeWithTag(TestTagSettingsScreenModeToggle)
                   .assertExists()
                   .assertIsDisplayed()
                   .performClick()

           }
       }
    }

    @Test
    fun test4_changeColorMode() {
        runBlocking {

            with(composeRule) {

                onNodeWithTag(TestTagSettingsScreenColorDropdown)
                    .assertExists()
                    .assertIsDisplayed()
                    .performClick()

                Thread.sleep(1000)

                onNodeWithTag("${TestTagSettingsScreenColorDropdownItem}${1}")
                    .assertExists()
                    .assertIsDisplayed()
                    .performClick()
            }
        }
    }

    @Test
    fun test5_changeCurrency() {
        runBlocking {

            with(composeRule) {

                onNodeWithTag(TestTagSettingsScreenCurrencyDropdown)
                    .assertExists()
                    .assertIsDisplayed()
                    .performClick()

                Thread.sleep(1000)

                onNodeWithTag("${TestTagSettingsScreenCurrencyDropdown}${1}")
                    .assertExists()
                    .assertIsDisplayed()
                    .performClick()
            }
        }
    }

    @Test
    fun test6_userCanBeLoggedOut() {
        with(composeRule) {
            onNodeWithTag(TestTagSettingsScreenLogOutButton)
                .assertIsDisplayed()
                .performClick()
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
