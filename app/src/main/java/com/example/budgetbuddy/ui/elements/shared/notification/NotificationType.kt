package com.example.budgetbuddy.ui.elements.shared.notification

import androidx.compose.ui.graphics.Color
import com.example.budgetbuddy.ui.theme.Green
import com.example.budgetbuddy.ui.theme.LightGreen
import com.example.budgetbuddy.ui.theme.LightRed
import com.example.budgetbuddy.ui.theme.Red

enum class NotificationType(
    val backgroundColor: Color,
    val borderColor: Color,
) {
    SUCCESS(
        backgroundColor = LightGreen,
        borderColor = Green,
    ),
    ERROR(
        backgroundColor = LightRed,
        borderColor = Red,
    )
}
