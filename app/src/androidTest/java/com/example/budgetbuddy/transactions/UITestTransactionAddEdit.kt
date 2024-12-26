package com.example.budgetbuddy.transactions

import junit.framework.TestCase.assertEquals
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.budgetbuddy.database.places.ILocalPlacesRepository
import com.example.budgetbuddy.navigation.Destination
import com.example.budgetbuddy.navigation.NavGraph
import com.example.budgetbuddy.ui.activity.MainActivity
import com.example.budgetbuddy.ui.elements.transactions.TestTagListOfTransactionsScreenListContainer
import com.example.budgetbuddy.ui.screens.auth.login.TestTagLoginScreenEmailInput
import com.example.budgetbuddy.ui.screens.auth.login.TestTagLoginScreenPasswordInput
import com.example.budgetbuddy.ui.screens.auth.login.TestTagLoginScreenSubmitButton
import com.example.budgetbuddy.ui.screens.greetings.TestTagGreetingsScreenSkip
import com.example.budgetbuddy.ui.screens.transactions.addEdit.*
import com.example.budgetbuddy.ui.screens.transactions.detail.*
import com.example.budgetbuddy.ui.screens.transactions.list.*
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.MockKAnnotations
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class UITestsTransactionAddEdit {

    @Inject
    lateinit var placesRepository: ILocalPlacesRepository

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

                TestCase.assertTrue(route == Destination.LoginScreen.route)
            }

            Thread.sleep(3000)
        }

        composeRule.activity.runOnUiThread {
            try {
                navController.navigate(Destination.TransactionsList.route)

                composeRule.waitForIdle()
            } catch (e: Exception) {
                println("Navigation failed: ${e.message}")
            }
        }

        composeRule.waitForIdle()
    }

    @Test
    fun test1_list_of_transactions_exists() {
        navigate(Destination.TransactionsList.route)
        with(composeRule) {

            waitForIdle()

            onNodeWithTag(TestTagListOfTransactionsScreenListContainer).assertExists()
            onNodeWithTag(TestTagListOfTransactionsScreenFAB).assertExists()

            Thread.sleep(1000)
        }
    }

    @Test
    fun test2_addMoneyViaFAB() {
        with(composeRule) {
            onNodeWithTag(TestTagListOfTransactionsScreenFAB)
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
        }
    }

    @Test
    fun test3_addInvalidData() {
        with(composeRule) {
            onNodeWithTag(TestTagListOfTransactionsScreenFAB)
                .assertExists()
                .assertIsDisplayed()
                .performClick()

            waitForIdle()

            onNodeWithTag(TestTagAddEditTransactionScreenTitle).assertIsDisplayed()

            onNodeWithTag(TestTagAddEditTransactionSaveButton)
                .performScrollTo()
                .assertIsDisplayed()
                .apply {
                    onNodeWithTag(TestTagAddEditTransactionSaveButton + "_submit")
                        .assertIsDisplayed()
                        .performClick()
                }

            waitForIdle()

            val route = navController.currentDestination?.route
            assertEquals(route, Destination.AddEditTransaction.route)
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
