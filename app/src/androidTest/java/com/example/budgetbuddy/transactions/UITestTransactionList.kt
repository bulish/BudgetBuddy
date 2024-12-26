package com.example.budgetbuddy.transactions

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
import com.example.budgetbuddy.ui.elements.shared.TestTagConfirmButton
import com.example.budgetbuddy.ui.elements.transactions.TestTagListOfTransactionsScreenListContainer
import com.example.budgetbuddy.ui.screens.auth.login.TestTagLoginScreenEmailInput
import com.example.budgetbuddy.ui.screens.auth.login.TestTagLoginScreenPasswordInput
import com.example.budgetbuddy.ui.screens.auth.login.TestTagLoginScreenSubmitButton
import com.example.budgetbuddy.ui.screens.greetings.TestTagGreetingsScreenSkip
import com.example.budgetbuddy.ui.screens.home.TestTagHomeScreenTransactionsFAB
import com.example.budgetbuddy.ui.screens.transactions.addEdit.TestTagAddEditTransactionCategoryInput
import com.example.budgetbuddy.ui.screens.transactions.addEdit.TestTagAddEditTransactionCurrencyInput
import com.example.budgetbuddy.ui.screens.transactions.addEdit.TestTagAddEditTransactionDateInput
import com.example.budgetbuddy.ui.screens.transactions.addEdit.TestTagAddEditTransactionNoteInput
import com.example.budgetbuddy.ui.screens.transactions.addEdit.TestTagAddEditTransactionPriceInput
import com.example.budgetbuddy.ui.screens.transactions.addEdit.TestTagAddEditTransactionSaveButton
import com.example.budgetbuddy.ui.screens.transactions.addEdit.TestTagAddEditTransactionScreenTitle
import com.example.budgetbuddy.ui.screens.transactions.addEdit.TestTagAddEditTransactionTransactionTypeInput
import com.example.budgetbuddy.ui.screens.transactions.detail.TestTagDetailTransactionCategory
import com.example.budgetbuddy.ui.screens.transactions.detail.TestTagDetailTransactionDate
import com.example.budgetbuddy.ui.screens.transactions.detail.TestTagDetailTransactionDeleteButton
import com.example.budgetbuddy.ui.screens.transactions.detail.TestTagDetailTransactionEditButton
import com.example.budgetbuddy.ui.screens.transactions.detail.TestTagDetailTransactionNote
import com.example.budgetbuddy.ui.screens.transactions.detail.TestTagDetailTransactionPlace
import com.example.budgetbuddy.ui.screens.transactions.detail.TestTagDetailTransactionPrice
import com.example.budgetbuddy.ui.screens.transactions.detail.TestTagDetailTransactionScreenTitle
import com.example.budgetbuddy.ui.screens.transactions.detail.TestTagDetailTransactionType
import com.example.budgetbuddy.ui.screens.transactions.list.TestTagListOfTransactionsScreenFAB
import com.example.budgetbuddy.ui.screens.transactions.list.TransactionEmptyList
import com.example.budgetbuddy.ui.screens.transactions.list.TransactionFilterItem
import com.example.budgetbuddy.ui.screens.transactions.list.TransactionLazyList
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
class UITestsTransactionList {

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
        }
    }

    @Test
    fun test3_list_of_transactions_exists() {
        navigate(Destination.TransactionsList.route)
        with(composeRule) {

            waitForIdle()

            onNodeWithTag(TestTagListOfTransactionsScreenListContainer).assertExists()
            onNodeWithTag(TestTagListOfTransactionsScreenListContainer).assertIsDisplayed()
            onNodeWithTag(TestTagListOfTransactionsScreenFAB).assertExists()

            onNodeWithTag(TestTagListOfTransactionsScreenFAB).performClick()

            Thread.sleep(1000)
        }
    }

    @Test
    fun test4_addMoneyAndUseFilter() {
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

            onNodeWithTag(TransactionFilterItem)
                .assertIsDisplayed()
                .performClick()

            onNodeWithTag(TransactionFilterItem + 5)
                .assertIsDisplayed()
                .performClick()

            onNodeWithTag(TransactionEmptyList)
                .assertIsDisplayed()
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
