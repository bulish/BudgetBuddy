package com.example.budgetbuddy.helpers

import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.budgetbuddy.ui.screens.auth.login.TestTagLoginScreenEmailInput
import com.example.budgetbuddy.ui.screens.auth.login.TestTagLoginScreenPasswordInput
import com.example.budgetbuddy.ui.screens.auth.login.TestTagLoginScreenSubmitButton
import com.example.budgetbuddy.navigation.Destination
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.NavHostController
import junit.framework.TestCase

object LoginHelper {

    fun performLogin(
        composeRule: ComposeTestRule,
        email: String,
        password: String,
        navController: NavHostController
    ) {
        with(composeRule) {
            // Zadejte email a heslo
            onNodeWithTag(TestTagLoginScreenEmailInput).performTextInput(email)
            onNodeWithTag(TestTagLoginScreenPasswordInput).performTextInput(password)

            // Klikněte na tlačítko přihlásit se
            onNodeWithTag(TestTagLoginScreenSubmitButton).performClick()

            // Počkejte na stabilizaci UI po kliknutí
            waitForIdle()

            // Zkontrolujte, že jste na správné obrazovce
            val route = navController.currentDestination?.route

            // Ověřte, že jsme na správné obrazovce
            TestCase.assertTrue(route == Destination.LoginScreen.route)
        }
    }
}
