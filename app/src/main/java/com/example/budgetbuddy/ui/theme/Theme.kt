package com.example.budgetbuddy.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel

private val DarkColorScheme = darkColorScheme(
    primary = Green,
    secondary = White,
    tertiary = Black,
    background = BlackBg,
)

private val LightColorScheme = lightColorScheme(
    primary = Green,
    secondary = Black,
    tertiary = White,
    background = LightBg,
)

@Composable
fun BudgetBuddyTheme(
    content: @Composable () -> Unit
) {

    val viewModel = hiltViewModel<ThemeViewModel>()
    val darkTheme by viewModel.isDarkMode.collectAsState()
    val colorScheme = if(darkTheme) DarkColorScheme else LightColorScheme

   val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}