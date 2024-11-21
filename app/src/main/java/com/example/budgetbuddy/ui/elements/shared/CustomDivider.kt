package com.example.budgetbuddy.ui.elements.shared

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.budgetbuddy.ui.theme.Black
import com.example.budgetbuddy.ui.theme.BorderWidth
import com.example.budgetbuddy.ui.theme.Grey
import com.example.budgetbuddy.ui.theme.LightGrey

@Composable
fun CustomDivider() {
    Divider (
        color = Grey,
        modifier = Modifier
            .height(BorderWidth())
            .fillMaxWidth()
    )
}
