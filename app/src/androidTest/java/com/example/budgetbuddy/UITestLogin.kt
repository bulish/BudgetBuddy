package com.example.budgetbuddy

import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.budgetbuddy.database.places.ILocalPlacesRepository
import com.example.budgetbuddy.navigation.Destination
import com.example.budgetbuddy.navigation.NavGraph
import com.example.budgetbuddy.ui.activity.MainActivity
import com.example.budgetbuddy.ui.elements.transactions.TestTagListOfTransactionsScreenListContainer
import com.example.budgetbuddy.ui.screens.auth.login.TestTagLoginScreenForm
import com.example.budgetbuddy.ui.screens.transactions.list.TestTagListOfTransactionsScreenFAB
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
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

    //  2 - HIGH
    @Test
    fun test_login_form_exists() {
        launchLoginScreenWithNavigation()
        with(composeRule) {
            onNodeWithTag(TestTagLoginScreenForm).assertExists()
            onNodeWithTag(TestTagLoginScreenForm).assertIsDisplayed()
            Thread.sleep(1000)
        }
    }

    //  1 - HIGH
    private fun launchLoginScreenWithNavigation() {
        composeRule.activity.setContent {
            MaterialTheme {
                navController = rememberNavController()
                NavGraph(startDestination = Destination.LoginScreen.route)
            }
        }
    }
}