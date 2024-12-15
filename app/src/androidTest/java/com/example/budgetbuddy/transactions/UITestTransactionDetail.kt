package com.example.budgetbuddy.transactions

import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.budgetbuddy.database.places.ILocalPlacesRepository
import com.example.budgetbuddy.navigation.Destination
import com.example.budgetbuddy.navigation.NavGraph
import com.example.budgetbuddy.ui.activity.MainActivity
import com.example.budgetbuddy.ui.elements.transactions.TestTagListOfTransactionsScreenListContainer
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
    fun test_list_of_transactions_exists() {
        launchListOfTransactionScreenWithNavigation()
        with(composeRule) {
            onNodeWithTag(TestTagListOfTransactionsScreenListContainer).assertExists()
            onNodeWithTag(TestTagListOfTransactionsScreenListContainer).assertIsDisplayed()
            onNodeWithTag(TestTagListOfTransactionsScreenFAB).assertExists()
            Thread.sleep(1000)
        }
    }

    /*
    //  3 - HIGH
    @Test
    fun test_navigate_to_correct_transaction_detail_screen() {
        runBlocking {
            val userId = FirebaseAuth.getInstance().currentUser?.uid
            assertNotNull(userId)

            launchListOfTransactionScreenWithNavigation()
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

                onNodeWithTag(TestTagPetDetailName).assertTextEquals(targetTransaction.name)
            }
        }
    }

    @Test
    fun test_scroll_and_navigate_to_correct_pet_detail_screen() {
        launchListOfPetsScreenWithNavigation()
        with(composeRule) {
            val targetPetName = "Carlitto"
            onNodeWithTag(TestTagListOfPetsScreenLazyList).assertIsDisplayed()
            onNodeWithTag(TestTagListOfPetsScreenLazyList).performScrollToNode(
                hasText(
                    targetPetName
                )
            )

            waitForIdle()
            Thread.sleep(1000) // Just to better see

            onNode(hasText(targetPetName)).assertIsDisplayed()
            onNode(hasText(targetPetName)).performClick()

            waitForIdle()
            Thread.sleep(1000)   // Just for better visibility

            val route = navController.currentBackStackEntry?.destination?.route
            Assert.assertTrue(route?.contains(RoutePetDetail) ?: false)

            onNodeWithTag(TestTagPetDetailName).assertTextEquals(targetPetName)
        }
    }

    @Test
    fun test_scroll_and_navigate_to_correct_pet_detail_screen_switch() {
        launchListOfPetsScreenWithNavigation()
        with(composeRule) {
            val targetPetName = "Carlitto"
            onNodeWithTag(TestTagListOfPetsScreenLazyList).assertIsDisplayed()
            onNodeWithTag(TestTagListOfPetsScreenLazyList).performScrollToNode(
                hasText(
                    targetPetName
                )
            )

            waitForIdle()
            Thread.sleep(1000) // Just to better see

            onNode(hasText(targetPetName)).assertIsDisplayed()
            onNode(hasText(targetPetName)).performClick()

            waitForIdle()
            Thread.sleep(1000)   // Just for better visibility

            val route = navController.currentBackStackEntry?.destination?.route
            Assert.assertTrue(route?.contains(RoutePetDetail) ?: false)

            onNodeWithTag(TestTagPetDetailName).assertTextEquals(targetPetName)

            onNodeWithTag(TestTagPetDetailWeightText).assertIsDisplayed()
            onNodeWithTag(TestTagPetDetailRadioButtonKilograms).assertIsDisplayed()
            onNodeWithTag(TestTagPetDetailRadioButtonPounds).assertIsDisplayed()

            onNodeWithTag(TestTagPetDetailWeightText).assertTextContains(
                value = WeightUnitsEnum.KILOGRAMS.unit,
                substring = true,
                ignoreCase = true
            )
            onNodeWithTag(TestTagPetDetailRadioButtonPounds).performClick()

            waitForIdle()
            Thread.sleep(1000)   // Just for better visibility

            onNodeWithTag(TestTagPetDetailWeightText).assertTextContains(
                value = WeightUnitsEnum.POUNDS.unit,
                substring = true,
                ignoreCase = true
            )
        }
    }*/

    //  1 - HIGH
    private fun launchListOfTransactionScreenWithNavigation() {
        composeRule.activity.setContent {
            MaterialTheme {
                navController = rememberNavController()
                NavGraph(startDestination = Destination.TransactionsList.route)
            }
        }
    }
}
