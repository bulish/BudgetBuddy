package com.example.budgetbuddy.ui.elements.settings.userIcon

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class UserIconType(val size: Dp, val fontSize: TextUnit, val showEmail: Boolean) {
    Small(24.dp, 12.sp, false),
    Large(48.dp, 18.sp, true)
}
