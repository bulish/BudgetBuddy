package com.example.budgetbuddy.ui.elements.shared.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.budgetbuddy.ui.theme.BasicMargin
import com.example.budgetbuddy.ui.theme.LightGrey
import com.example.budgetbuddy.ui.theme.White
import com.example.budgetbuddy.ui.theme.Black

enum class CustomButtonType {
    OutlinedMaxSize,
    OutlinedMaxSizeLight,
    Basic,
    Outlined
}

@Composable
fun getCustomButtonType(buttonType: CustomButtonType): CustomButtonStyle {
    val colorScheme = MaterialTheme.colorScheme

    return when (buttonType) {
        CustomButtonType.OutlinedMaxSize -> CustomButtonStyle(
            modifier = Modifier.fillMaxWidth().padding(horizontal = BasicMargin()),
            contentColor = White,
            containerColor = colorScheme.primary,
            strokeColor = colorScheme.primary
        )
        CustomButtonType.OutlinedMaxSizeLight -> CustomButtonStyle(
            modifier = Modifier.fillMaxWidth().padding(horizontal = BasicMargin()),
            contentColor = Black,
            containerColor = White,
            strokeColor = LightGrey
        )
        CustomButtonType.Basic -> CustomButtonStyle(
            modifier = Modifier,
            contentColor = White,
            containerColor = colorScheme.primary,
            strokeColor = colorScheme.primary
        )
        CustomButtonType.Outlined -> CustomButtonStyle(
            modifier = Modifier,
            contentColor = Black,
            containerColor = White,
            strokeColor = Black
        )
    }
}

data class CustomButtonStyle(
    val modifier: Modifier,
    val contentColor: Color,
    val containerColor: Color,
    val strokeColor: Color
)
