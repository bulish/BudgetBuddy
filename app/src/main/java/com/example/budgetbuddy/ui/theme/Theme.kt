package com.example.budgetbuddy.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Green,
    secondary = White,
    tertiary = Black
)

private val LightColorScheme = lightColorScheme(
    primary = Green,
    secondary = Black,
    tertiary = White
)

@Composable
fun BudgetBuddyTheme(
    content: @Composable () -> Unit
) {

    /*    val viewModel = hiltViewModel<ThemeViewModel>()
    val darkTheme by viewModel.isDarkMode.collectAsState()

    val colorScheme = if(darkTheme) DarkColorScheme else LightColorScheme

    }*/

    val colorScheme = LightColorScheme


   /* val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }*/

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}