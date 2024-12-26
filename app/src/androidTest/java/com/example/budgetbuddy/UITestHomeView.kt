package com.example.budgetbuddy

import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.budgetbuddy.navigation.Destination
import com.example.budgetbuddy.navigation.NavGraph
import com.example.budgetbuddy.ui.activity.MainActivity
import com.example.budgetbuddy.ui.elements.shared.TestTagConfirmButton
import com.example.budgetbuddy.ui.screens.auth.login.TestTagLoginScreenEmailInput
import com.example.budgetbuddy.ui.screens.auth.login.TestTagLoginScreenPasswordInput
import com.example.budgetbuddy.ui.screens.auth.login.TestTagLoginScreenSubmitButton
import com.example.budgetbuddy.ui.screens.greetings.TestTagGreetingsScreenSkip
import com.example.budgetbuddy.ui.screens.home.*
import com.example.budgetbuddy.ui.screens.transactions.addEdit.*
import com.example.budgetbuddy.ui.screens.transactions.detail.*
import com.example.budgetbuddy.ui.screens.transactions.list.TransactionEmptyList
import com.example.budgetbuddy.ui.screens.transactions.list.TransactionLazyList
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@ExperimentalCoroutinesApi
@HiltAndroidTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class UITestHomeView {

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
                navController.navigate(Destination.HomeScreen.route)

                composeRule.waitForIdle()
            } catch (e: Exception) {
                println("Navigation failed: ${e.message}")
            }
        }

        composeRule.waitForIdle()
    }

    @Test
    fun test1_homeViewScreenIsLoaded() {
        with(composeRule) {
            onNodeWithTag(TestTagHomeScreenTransactionsFAB).assertIsDisplayed()
            onNodeWithTag(TestTagHomeScreenAddMoneyButton).assertIsDisplayed()
            onNodeWithTag(TestTagHomeScreenTransactionsTitle).assertIsDisplayed()
        }
    }

    @Test
    fun test2_addMoneyViaBalanceBox() {
        with(composeRule) {
            onNodeWithTag(TestTagHomeScreenAddMoneyButton)
                .assertExists()
                .assertIsDisplayed()
                .performClick()

            waitForIdle()

            onNodeWithTag(TestTagAddEditTransactionScreenTitle).assertIsDisplayed()

            onNodeWithTag(TestTagAddEditTransactionCategoryInput)
                .assertIsDisplayed()
                .performClick()

            onNodeWithTag(TestTagAddEditTransactionCategoryInput + 1)
                .assertIsDisplayed()
                .performClick()

            onNodeWithTag(TestTagAddEditTransactionPriceInput)
                .assertIsDisplayed()
                .performTextInput("500")

            onNodeWithTag(TestTagAddEditTransactionCurrencyInput)
                .assertIsDisplayed()
                .performClick()

            onNodeWithTag(TestTagAddEditTransactionCurrencyInput + 1)
                .assertIsDisplayed()
                .performClick()

            onNodeWithTag(TestTagAddEditTransactionTransactionTypeInput + 0)
                .assertIsDisplayed()
                .performClick()

            onNodeWithTag(TestTagAddEditTransactionDateInput).assertIsDisplayed()

            onNodeWithTag(TestTagAddEditTransactionNoteInput)
                .assertIsDisplayed()
                .performTextInput("Note text")

            onNodeWithTag(TestTagAddEditTransactionSaveButton)
                .performScrollTo()
                .assertIsDisplayed()
                .apply {
                    onNodeWithTag(TestTagAddEditTransactionSaveButton + "_submit")
                        .assertIsDisplayed()
                        .performClick()
                }

            waitForIdle()

            Thread.sleep(1000)

            onNodeWithTag(TransactionLazyList)
                .assertIsDisplayed()

            onNodeWithTag(TransactionLazyList + 0)
                .assertIsDisplayed()
                .performClick()

            waitForIdle()

            onNodeWithTag(TestTagDetailTransactionScreenTitle).isDisplayed()

            onNodeWithTag(TestTagDetailTransactionCategory)
                .assertTextContains("Business")

            onNodeWithTag(TestTagDetailTransactionPrice)
                .assertTextContains("- 5.000 AED")

            onNodeWithTag(TestTagDetailTransactionType)
                .assertTextContains("Expense")

            val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.US)
            val currentDate = dateFormat.format(Date())

            onNodeWithTag(TestTagDetailTransactionDate)
                .assertTextContains(currentDate)

            onNodeWithTag(TestTagDetailTransactionNote)
                .assertTextContains("Note text")

            onNodeWithTag(TestTagDetailTransactionPlace)
                .assertTextContains("-")

            onNodeWithTag(TestTagDetailTransactionDeleteButton)
                .assertIsDisplayed()
                .performClick()

            onNodeWithTag(TestTagConfirmButton)
                .assertIsDisplayed()
                .performClick()

            waitForIdle()

            onNodeWithTag(TransactionEmptyList)
                .assertIsDisplayed()
        }
    }

    @Test
    fun test3_addMoneyViaFAB() {
        with(composeRule) {
            onNodeWithTag(TestTagHomeScreenTransactionsFAB)
                .assertExists()
                .assertIsDisplayed()
                .performClick()

            waitForIdle()

            onNodeWithTag(TestTagAddEditTransactionScreenTitle).assertIsDisplayed()

            onNodeWithTag(TestTagAddEditTransactionCategoryInput)
                .assertIsDisplayed()
                .performClick()

            onNodeWithTag(TestTagAddEditTransactionCategoryInput + 1)
                .assertIsDisplayed()
                .performClick()

            onNodeWithTag(TestTagAddEditTransactionPriceInput)
                .assertIsDisplayed()
                .performTextInput("500")

            onNodeWithTag(TestTagAddEditTransactionCurrencyInput)
                .assertIsDisplayed()
                .performClick()

            onNodeWithTag(TestTagAddEditTransactionCurrencyInput + 1)
                .assertIsDisplayed()
                .performClick()

            onNodeWithTag(TestTagAddEditTransactionTransactionTypeInput + 0)
                .assertIsDisplayed()
                .performClick()

            onNodeWithTag(TestTagAddEditTransactionDateInput).assertIsDisplayed()

            onNodeWithTag(TestTagAddEditTransactionNoteInput)
                .assertIsDisplayed()
                .performTextInput("Note text")

            onNodeWithTag(TestTagAddEditTransactionSaveButton)
                .performScrollTo()
                .assertIsDisplayed()
                .apply {
                    onNodeWithTag(TestTagAddEditTransactionSaveButton + "_submit")
                        .assertIsDisplayed()
                        .performClick()
                }

            waitForIdle()

            Thread.sleep(1000)

            onNodeWithTag(TransactionLazyList)
                .assertIsDisplayed()

            onNodeWithTag(TransactionLazyList + 0)
                .assertIsDisplayed()
                .performClick()

            waitForIdle()

            onNodeWithTag(TestTagDetailTransactionScreenTitle).isDisplayed()

            onNodeWithTag(TestTagDetailTransactionCategory)
                .assertTextContains("Business")

            onNodeWithTag(TestTagDetailTransactionPrice)
                .assertTextContains("- 5.000 AED")

            onNodeWithTag(TestTagDetailTransactionType)
                .assertTextContains("Expense")

            val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.US)
            val currentDate = dateFormat.format(Date())

            onNodeWithTag(TestTagDetailTransactionDate)
                .assertTextContains(currentDate)

            onNodeWithTag(TestTagDetailTransactionNote)
                .assertTextContains("Note text")

            onNodeWithTag(TestTagDetailTransactionPlace)
                .assertTextContains("-")

            onNodeWithTag(TestTagDetailTransactionDeleteButton)
                .assertIsDisplayed()
                .performClick()

            onNodeWithTag(TestTagConfirmButton)
                .assertIsDisplayed()
                .performClick()

            waitForIdle()

            onNodeWithTag(TransactionEmptyList)
                .assertIsDisplayed()
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
