package com.example.budgetbuddy.transactions

import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.budgetbuddy.database.places.ILocalPlacesRepository
import com.example.budgetbuddy.helpers.LoginHelper.performLogin
import com.example.budgetbuddy.navigation.Destination
import com.example.budgetbuddy.navigation.NavGraph
import com.example.budgetbuddy.ui.activity.MainActivity
import com.example.budgetbuddy.ui.elements.transactions.TestTagListOfTransactionsScreenListContainer
import com.example.budgetbuddy.ui.screens.auth.login.TestTagLoginScreenEmailInput
import com.example.budgetbuddy.ui.screens.auth.login.TestTagLoginScreenForgottenPassword
import com.example.budgetbuddy.ui.screens.auth.login.TestTagLoginScreenForm
import com.example.budgetbuddy.ui.screens.auth.login.TestTagLoginScreenPasswordInput
import com.example.budgetbuddy.ui.screens.auth.login.TestTagLoginScreenSignUpButton
import com.example.budgetbuddy.ui.screens.auth.login.TestTagLoginScreenSubmitButton
import com.example.budgetbuddy.ui.screens.transactions.list.TestTagListOfTransactionsScreenFAB
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.Assert
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class UITestsTransactionDetail {

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
    }

    //  2 - HIGH

    @Test
    fun test1_login() {
        navigate(Destination.LoginScreen.route)
        with(composeRule) {
            val email = "test@test.com"
            val password = "Test12345"

            onNodeWithTag(TestTagLoginScreenEmailInput).assertExists()
            onNodeWithTag(TestTagLoginScreenPasswordInput).assertExists()
            onNodeWithTag(TestTagLoginScreenSubmitButton).assertExists()

            onNodeWithTag(TestTagLoginScreenEmailInput).performTextInput(email)
            onNodeWithTag(TestTagLoginScreenPasswordInput).performTextInput(password)
            onNodeWithTag(TestTagLoginScreenSubmitButton).performClick()
        }
    }

    @Test
    fun test2_list_of_transactions_exists() {
        navigate(Destination.TransactionsList.route)
        with(composeRule) {

            waitForIdle()

            onNodeWithTag(TestTagListOfTransactionsScreenListContainer).assertExists()
            onNodeWithTag(TestTagListOfTransactionsScreenFAB).assertExists()

            Thread.sleep(1000)
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

    //  3 - HIGH
   /* @Test
    fun test4_navigate_to_correct_transaction_detail_screen() {
        runBlocking {
            val userId = FirebaseAuth.getInstance().currentUser?.uid
            assertNotNull(userId)


            with(composeRule) {
                val transactions =
                    placesRepository.getAllByUser(userId!!).first { it.isNotEmpty() }
                val targetTransaction =
                    transactions.first { transaction -> transaction.name == "Afrodita" }

                assert(targetTransaction.id != null)

                onNodeWithTag(TestTagListOfTransactionsScreenListContainer).assertIsDisplayed()

                onNode(hasText(targetTransaction.name)).assertIsDisplayed()
                onNode(hasText(targetTransaction.name)).performClick()
                waitForIdle()

                Thread.sleep(1000)
                val route = navController.currentBackStackEntry?.destination?.route
                Assert.assertTrue(route?.contains(Destination.TransactionDetail.route) ?: false)

                //onNodeWithTag(TestTagPetDetailName).assertTextEquals(targetTransaction.name)
            }
        }
    }*/

    // Helper method to launch the transaction screen and navigate
    private fun navigate(route: String) {
        composeRule.activity.setContent {
            MaterialTheme {
                navController = rememberNavController()
                NavGraph(navController = navController, startDestination = route)
            }
        }
    }


}
