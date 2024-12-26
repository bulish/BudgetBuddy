package com.example.budgetbuddy.places

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
import com.example.budgetbuddy.ui.screens.auth.login.*
import com.example.budgetbuddy.ui.screens.greetings.TestTagGreetingsScreenSkip
import com.example.budgetbuddy.ui.screens.places.addEditPlace.*
import com.example.budgetbuddy.ui.screens.places.map.*
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
class UITestMapScreen {

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
                navController.navigate(Destination.MapScreen.route)

                composeRule.waitForIdle()
            } catch (e: Exception) {
                println("Navigation failed: ${e.message}")
            }
        }

        composeRule.waitForIdle()
    }

    @Test
    fun test1_map_exists() {
        navigate(Destination.MapScreen.route)
        with(composeRule) {

            waitForIdle()

            onNodeWithTag(TestTagMapScreenGoogleMap).assertExists()
            onNodeWithTag(TestTagMapScreenFAB).assertExists()

            Thread.sleep(1000)
        }
    }

    @Test
    fun test2_addPlaceViaFAB() {
        with(composeRule) {
            navigate(Destination.MapScreen.route)

            waitForIdle()

            onNodeWithTag(TestTagMapScreenFAB)
                .assertExists()
                .assertIsDisplayed()
                .performClick()

            waitForIdle()

            onNodeWithTag(TestTagAddEditPlaceScreenTitle).assertIsDisplayed()

            onNodeWithTag(TestTagAddEditPlaceScreenNameInput)
                .assertIsDisplayed()
                .performTextInput("Optika")

            onNodeWithTag(TestTagAddEditPlaceScreenCategoryInput)
                .assertIsDisplayed()
                .performClick()

            onNodeWithTag(TestTagAddEditPlaceScreenCategoryInput + 2)
                .assertIsDisplayed()
                .performClick()

            onNodeWithTag(TestTagAddEditPlaceScreenSaveInput)
                .performScrollTo()
                .assertIsDisplayed()
                .apply {
                    onNodeWithTag(TestTagAddEditPlaceScreenSaveInput + "_submit")
                        .assertIsDisplayed()
                        .performClick()
                }

            waitForIdle()
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
